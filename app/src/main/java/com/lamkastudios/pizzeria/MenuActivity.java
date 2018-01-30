package com.lamkastudios.pizzeria;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lamkastudios.pizzeria.POJOS.Ingrediente;
import com.lamkastudios.pizzeria.POJOS.Pedido;
import com.lamkastudios.pizzeria.POJOS.Pizza;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    //FRAGMENTO

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //Establezco las constantes key del bundle
        private static final String ARG_SECTION_NUMBER = "section_number";

        //DATOS. v1.0=FIJO - v2=BD
        private static final int[] nombresPizza = new int[]{
                R.string.pepperoni,
                R.string.quesos,
                R.string.estaciones
        };

        private String[] masas;

        private static final int[] descripcionPizza = new int[]{
                R.string.desPepperoni,
                R.string.desQuesos,
                R.string.desEstaciones
        };
        private static final int[] precioPizza = new int[]{
                6,
                7,
                8
        };

        private static final Ingrediente[][] ingredientes = new Ingrediente[][]{
                {new Ingrediente("Pepperoni",0.8)},
                {new Ingrediente("4 Quesos", 1.2), new Ingrediente("Oregano",0.5)},
                {new Ingrediente("Aceitunas",0.2),new Ingrediente("Jamon",1.3)}
        };

        private static final int[] imgPizza = new int[]{
                R.drawable.pizza,
                R.drawable.pizza,
                R.drawable.pizza
        };
        private static final int[] tams = new int[]{R.id.rdPequeña,R.id.rdMediana,R.id.rdGrande};
        private static final String[] nombreTams = new String[]{"Pequeña","Mediana","Grande"};
        private static final int[] preciosTam = new int[]{2,3,5};
        private double precioTotal;
        private String tamPizza;
        private int posicion;

        //Constructor vacío por que es un Singleton, sólo queremos 1 instancia de cada fragment
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        //Segunda llamada desde el adapter
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            //El bundle son parámetros para un activity
            Bundle args = new Bundle();

            //Le meto a los argumentos, que luego le paso al onCreateView.
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            //Le asocio los argumentos al fragment
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            //le asignamos el fragment_menu
            View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

            //Obtengo los argumentos pasados por la instancia anterior
            Bundle args = getArguments();

            //Obtenemos los datos de donde nos encontramos
            posicion = args.getInt(ARG_SECTION_NUMBER)-1;
            precioTotal=0;
            tamPizza="";
            masas = getResources().getStringArray(R.array.masas);

            //Seteamos la info dinámica
            for(int i=0;i<tams.length;i++)
            {
                if(getActivity().getIntent().getExtras().getInt("tam")==tams[i])
                {
                    tamPizza=nombreTams[i];
                    precioTotal+=preciosTam[i]+precioPizza[posicion];
                }
            }

            ((TextView)rootView.findViewById(R.id.txtNombrePizza)).setText(nombresPizza[posicion]);
            ((TextView)rootView.findViewById(R.id.txtDescripcion)).setText(descripcionPizza[posicion]);
            ((TextView)rootView.findViewById(R.id.precio)).setText(String.valueOf(precioTotal));
            ((ImageView)rootView.findViewById(R.id.imgPizza)).setImageResource(imgPizza[posicion]);

            //LISTENER
            rootView.findViewById(R.id.btnAñadirPizza).setOnClickListener(AñadirListener);
            rootView.findViewById(R.id.btnConfirmar).setOnClickListener(AñadirListener);

            return rootView;
        }

        public View.OnClickListener AñadirListener = new View.OnClickListener() {
            @Override
            public void onClick(View b)
            {
                String nombre = getActivity().getResources().getString(nombresPizza[posicion]);
                Pizza p = new Pizza(nombre,masas[posicion],nombreTams[posicion],precioTotal,ingredientes[posicion]);
                Pedido.pizzas.add(p);
                Toast.makeText(getActivity().getApplicationContext(), "Pizza añadida", Toast.LENGTH_SHORT).show();
                if(b.getId()==R.id.btnAñadirPizza)
                {
                    Intent i = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else
                {
                    Intent i = new Intent(getActivity().getApplicationContext(),PedidoActivity.class);
                    i.putExtra("repetir",false);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        };
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    //ADAPTER FRAGMENT. CARGA CONTENIDO DINÁMICO
    //Se inicia primero y nos devuelve un PlaceholderFragment dependiendo de la posición
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        //Se llama a este método primero y nos dice en qué fragment estamos
        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
