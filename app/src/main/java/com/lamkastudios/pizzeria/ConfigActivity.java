package com.lamkastudios.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.lamkastudios.pizzeria.POJOS.Pedido;
import com.lamkastudios.pizzeria.POJOS.Pizza;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView fondo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        fondo = findViewById(R.id.fondo);
        ((Switch)findViewById(R.id.cambiarFondo)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = MainActivity.pref.edit();
                if(b)
                {
                    fondo.setImageResource(R.drawable.pizzafondo);
                    fondo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    editor.putInt("fondo",R.drawable.pizzafondo);
                }
                else{
                    fondo.setImageResource(R.drawable.pizzafondo2);
                    fondo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    editor.putInt("fondo",R.drawable.pizzafondo2);
                }
                editor.apply();

            }
        });
        findViewById(R.id.btnApagar).setOnClickListener(this);
        findViewById(R.id.btnGuardar).setOnClickListener(this);
    }

    @Override
    public void onClick(View b)
    {
        if(b.getId()==R.id.btnGuardar)
        {

            SharedPreferences pref = getSharedPreferences("datos",0);
            SharedPreferences.Editor editor = pref.edit();

            //GUARDAR USUARIO
            EditText usuario = (EditText) findViewById(R.id.txtUsuario);
            EditText password = (EditText) findViewById(R.id.txtPass);

            editor.putString("usuario",usuario.getText().toString());
            editor.putString("contraseña",password.getText().toString());

            editor.apply();
            Toast.makeText(this, "Preferencias actualizadas", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        else if(b.getId()==R.id.btnApagar)
        {
            finishAffinity();
        }
    }
}
