package com.lamkastudios.pizzeria;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActivityPizza extends AppCompatActivity implements View.OnClickListener{

    private Class next;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        findViewById(R.id.btnContinuar).setOnClickListener(this);
        findViewById(R.id.btnVolver).setOnClickListener(this);

        try
        {
            if(getIntent().getExtras().getBoolean("menu"))
            {
                next = Class.forName(MenuActivity.class.getName());
            }
            else{
                next = Class.forName(ActivityIngredientes.class.getName());
            }
        }
        catch(ClassNotFoundException e)
        {
            Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onClick(View b)
    {
        RadioGroup grupo = findViewById(R.id.groupTam);
        if(b.getId()==R.id.btnContinuar)
        {
            if(grupo.getCheckedRadioButtonId()!=-1)
            {
                Intent i = new Intent(getApplicationContext(),next);
                i.putExtra("tam",grupo.getCheckedRadioButtonId());
                startActivity(i);
                this.finish();
            }
            else
            {
                //Otro estilo de Toast, más cuadrado
                Snackbar.make(b,"Seleccione un tamaño",Snackbar.LENGTH_SHORT).show();
            }
        }
        else if(b.getId()==R.id.btnVolver)
        {
           startActivity(new Intent(this,MainActivity.class));
           this.finish();
        }
    }
}
