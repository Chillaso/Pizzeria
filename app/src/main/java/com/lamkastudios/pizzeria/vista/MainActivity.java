package com.lamkastudios.pizzeria.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lamkastudios.pizzeria.DAO.RealmConnector;
import com.lamkastudios.pizzeria.Modelo.Usuario;
import com.lamkastudios.pizzeria.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText u;
    private EditText p;
    public static SharedPreferences pref;
    private AlertDialog alerta;
    public static RealmConnector rc;
    public static Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIO DEL REALM CONNECTOR
        rc = RealmConnector.Builder(getApplicationContext());

        pref = getSharedPreferences("datos",0);
        ((ImageView)findViewById(R.id.fondo)).setImageResource(pref.getInt("fondo",R.drawable.pizzafondo));

        findViewById(R.id.btnPizza).setOnClickListener(this);
        findViewById(R.id.btnMenu).setOnClickListener(this);
        findViewById(R.id.btnRepetir).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


                        //Obtenemos al usuario con los datos introducidos
                        Usuario usuario = rc.obtenerUsuario(u.getText().toString(),p.getText().toString());

                        //Comprobamos que hay algun usuario con los datos introducidos
                        if(usuario!=null)
                        {
                            Snackbar.make(findViewById(R.id.btnRepetir),"Inicio de sesión correcto",Snackbar.LENGTH_SHORT).show();
                            alerta.dismiss();
                            usuarioActual=usuario;
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pedidos)
        {

        }
        else if (id == R.id.inicio)
        {
            InicioSesion();
        }
        else if (id == R.id.opciones)
        {
            Intent i = new Intent(getApplicationContext(),ConfigActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}