package com.lamkastudios.pizzeria;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText u;
    private EditText p;
    public static SharedPreferences pref;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("datos",0);
        ((ImageView)findViewById(R.id.fondo)).setImageResource(pref.getInt("fondo",R.drawable.pizzafondo));

        findViewById(R.id.btnPizza).setOnClickListener(this);
        findViewById(R.id.btnMenu).setOnClickListener(this);
        findViewById(R.id.btnRepetir).setOnClickListener(this);
        findViewById(R.id.btnAjustes).setOnClickListener(this);
        findViewById(R.id.btnUser).setOnClickListener(this);
    }

    private void InicioSesion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertaView = inflater.inflate(R.layout.inicio_dialog,null); //Layout

        u = alertaView.findViewById(R.id.user);
        p = alertaView.findViewById(R.id.pass);

        builder.setView(alertaView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String user = pref.getString("usuario","");
                        String pass = pref.getString("contraseña","");

                        if(u.getText().toString().equals(user) && p.getText().toString().equals(pass))
                        {
                            Snackbar.make(findViewById(R.id.btnRepetir),"Inicio de sesión correcto",Snackbar.LENGTH_SHORT).show();
                            alerta.dismiss();
                        }
                        else
                        {
                            Snackbar.make(findViewById(R.id.btnRepetir),"Error al iniciar sesión",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alerta.dismiss();
                    }
                });
        //Alerta
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btnPizza)
        {
            Intent i = new Intent(getApplicationContext(),ActivityPizza.class);
            //Pasamos info de una activity a otra
            i.putExtra("menu",false);
            startActivity(i);
        }
        else if(view.getId()==R.id.btnMenu)
        {
            Intent i = new Intent(this,ActivityPizza.class);
            i.putExtra("menu",true);
            startActivity(i);
        }
        else if(view.getId()==R.id.btnRepetir)
        {
            if(pref.getString("nombre","").equals(""))
            {
                Toast.makeText(this, "No hay última pizza disponible", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent i = new Intent(this,PedidoActivity.class);
                i.putExtra("repetir",true);
                startActivity(i);
            }

        }
        else if(view.getId()==R.id.btnAjustes)
        {
            Intent i = new Intent(getApplicationContext(),ConfigActivity.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.btnUser)
        {
            InicioSesion();
        }
    }
}
