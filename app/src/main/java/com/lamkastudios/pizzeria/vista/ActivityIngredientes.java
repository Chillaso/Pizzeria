package com.lamkastudios.pizzeria.vista;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lamkastudios.pizzeria.Modelo.IncompatibilidadException;
import com.lamkastudios.pizzeria.Modelo.Ingrediente;
import com.lamkastudios.pizzeria.Modelo.Pizza;
import com.lamkastudios.pizzeria.Modelo.PedidoInter;
import com.lamkastudios.pizzeria.R;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.RealmList;

public class ActivityIngredientes extends AppCompatActivity implements View.OnClickListener{

    private String[] masas;
    //CONSTANTES. DEBERIAN DE VENIR DE UNA BD. VERSION 1.0
    private static final int[] tams = new int[]{R.id.rdPequeña,R.id.rdMediana,R.id.rdGrande};
    private static final String[] tamsString = new String[]{"Pequeña","Mediana","Grande"};
    private static final int[] preciosTam = new int[]{2,3,5};
    private static final double[] preciosIngredientes = new double[]{1,1.2,1.5};
    private static final int[] preciosMasa = new int[]{2,3,4};
    private static final int[] chkBox = new int[]{
            R.id.chkCebolla,
            R.id.chkCarne,
            R.id.chkChorizo,
            R.id.chkChampi,
            R.id.chkQuesoCabra,
            R.id.chkPimiento,
            R.id.chkPepperoni,
            R.id.chk4Quesos,
            R.id.chkSalchicha
    };

    private double precioBase;
    private double precioTotal;
    private double ingredientesTam;
    private RealmList<Ingrediente> ingredientesPizza;
    private String tamPizza;
    private ArrayList<Ingrediente> ingredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        //INICIALIZA
        tamPizza="";
        //LISTA DE INGREDIENTES REALM
        ingredientes = MainActivity.rc.cargarIngredientes();
        ingredientesPizza= new RealmList<Ingrediente>();
        findViewById(R.id.cajaNombre).clearFocus();

        //AGREGAR MASAS
        Resources r = getResources();
        masas=r.getStringArray(R.array.masas);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.texto_spinner,masas);
        Spinner spinner = findViewById(R.id.spinnerMasa);
        spinner.setAdapter(adapter);

        //DEFINICIÓN PRECIOS
        for(int i=0;i<tams.length;i++)
        {
            if(getIntent().getExtras().getInt("tam")==tams[i])
            {
                tamPizza=tamsString[i];
                precioBase=preciosTam[i];
                ingredientesTam=preciosIngredientes[i];
            }
        }
        ((TextView)findViewById(R.id.txtPrecio)).setText(String.valueOf(precioBase));

        //LISTENERS
        findViewById(R.id.btnConfirmar).setOnClickListener(this);
        findViewById(R.id.btnVolver).setOnClickListener(this);
        findViewById(R.id.btnAñadirPizza).setOnClickListener(this);
        ((CheckBox)findViewById(R.id.chkCebolla)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkCarne)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkChampi)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkChorizo)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkQuesoCabra)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkPepperoni)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkPimiento)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chk4Quesos)).setOnCheckedChangeListener(Ingrediente);
        ((CheckBox)findViewById(R.id.chkSalchicha)).setOnCheckedChangeListener(Ingrediente);
        spinner.setOnItemSelectedListener(Seleccion);
    }

    //LISTENER DE LOS CHKBOX. AÑADIR/ELIMINAR INGREDIENTES
    private CompoundButton.OnCheckedChangeListener Ingrediente = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton box, boolean b)
        {

            double precioIngrediente=0;
            try{
                for(int i=0; i<chkBox.length;i++)
                {
                    if(box.getId()==chkBox[i])
                    {
                        checkIncompatible(box);
                        precioIngrediente = ingredientes.get(i).getPrecio()*ingredientesTam;
                        if(b)
                        {
                            precioTotal+=precioIngrediente;
                            ingredientesPizza.add(ingredientes.get(i));
                        }
                        else
                        {
                            precioTotal-=precioIngrediente;
                            ingredientesPizza.remove(ingredientes.get(i));
                        }
                    }
                }
                //FORMATEO Y SALIDA TEXTO DEL PRECIO
                DecimalFormat df = new DecimalFormat("#.00");
                ((TextView)findViewById(R.id.txtPrecio)).setText(df.format(precioTotal));
            }
            catch (IncompatibilidadException e)
            {
                Toast.makeText(ActivityIngredientes.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private AdapterView.OnItemSelectedListener Seleccion = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
           precioTotal=precioBase;
           precioTotal+=preciosMasa[i];
            ((TextView)findViewById(R.id.txtPrecio)).setText(String.valueOf(precioTotal));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btnConfirmar)
        {
            Pizza p = crearPizza();
            if(p!=null)
            {
                PedidoInter.pizzas.add(p);

                Intent i = new Intent(this,PedidoActivity.class);
                i.putExtra("repetir",false);
                startActivity(i);
            }
        }
        else if(view.getId()==R.id.btnVolver)
        {
            Intent i = new Intent(getApplicationContext(),ActivityPizza.class);
            i.putExtra("menu",false);
            startActivity(i);
            this.finish();
        }
        else if(view.getId()==R.id.btnAñadirPizza)
        {
            Pizza p = crearPizza();
            if(p!=null)
            {
                Toast.makeText(this, "Pizza añadida", Toast.LENGTH_SHORT).show();
                PedidoInter.pizzas.add(p);

                Intent i = new Intent(getApplicationContext(),ActivityPizza.class);
                i.putExtra("menu",false);
                startActivity(i);
                finish();
            }
        }
    }

    private Pizza crearPizza()
    {
        try
        {
            String nombre = ((EditText)findViewById(R.id.cajaNombre)).getText().toString();
            String masa = ((Spinner)findViewById(R.id.spinnerMasa)).getSelectedItem().toString();
            Pizza p = new Pizza(nombre,masa,tamPizza,precioTotal,ingredientesPizza);
            return p;
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            return null;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private void checkIncompatible(CompoundButton b) throws IncompatibilidadException
    {
        //Incompatibilidades
        CheckBox incompatible=null;
        if(b.getId()==R.id.chk4Quesos)
        {
            incompatible = findViewById(R.id.chkQuesoCabra);
        }
        else if(b.getId()==R.id.chkQuesoCabra)
        {
            incompatible = findViewById(R.id.chk4Quesos);
        }

        //Controles
        if((b.getId()==R.id.chkQuesoCabra||b.getId()==R.id.chk4Quesos) && b.isChecked() && incompatible.isChecked())
        {
            b.setChecked(false);
            throw new IncompatibilidadException();
        }
    }
}
