package com.lamkastudios.pizzeria.DAO;
//Created by chillaso All rights reserved.


import android.content.Context;

import com.lamkastudios.pizzeria.Modelo.Ingrediente;
import com.lamkastudios.pizzeria.Modelo.Menu;
import com.lamkastudios.pizzeria.Modelo.Pedido;
import com.lamkastudios.pizzeria.Modelo.PedidoInter;
import com.lamkastudios.pizzeria.Modelo.Pizza;
import com.lamkastudios.pizzeria.Modelo.Usuario;
import com.lamkastudios.pizzeria.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmConnector {

    private static RealmConnector rc;
    private Realm r;

    private RealmConnector(Context c)
    {
        Realm.init(c);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("pizzeria.realm")
                .schemaVersion(1)
                .build();
        this.r = Realm.getInstance(config);

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales()
    {
        //PIZZA MENU
        RealmList<Ingrediente> i1 = new RealmList<>();
        i1.add(new Ingrediente("Cebolla",1));
        i1.add(new Ingrediente("Carne",1.2));
        Pizza p = new Pizza("Barbacoa","Normal","Extrem",10.5,R.string.desQuesos,i1);
        RealmList<Ingrediente> i2 = new RealmList<>();
        i1.add(new Ingrediente("Cebolla",1));
        i1.add(new Ingrediente("Pepperoni",1.2));
        Pizza p2 = new Pizza("Pepperoni","Normal","Mediana",8.5,R.string.desPepperoni,i2);
        RealmList<Ingrediente> i3 = new RealmList<>();
        i1.add(new Ingrediente("Tomate",1.2));
        i1.add(new Ingrediente("Aceitunas",1.2));
        Pizza p3 = new Pizza("Estaciones","Normal","Extrem",8.5,R.string.desEstaciones,i3);

        RealmList<Pizza> menu = new RealmList<>();
        menu.add(p);
        menu.add(p2);
        menu.add(p3);

        //IMAGENES
        RealmList<Integer> imagenes = new RealmList<>();
        imagenes.add(R.drawable.bbq);
        imagenes.add(R.drawable.pepperoni);
        imagenes.add(R.drawable.estaciones);

        final Menu m = new Menu(menu,imagenes);
        final Ingrediente ii1,ii2,ii3,i4,i5,i7,i8,i9,i10;

        ii1=new Ingrediente("Cebolla",1);
        ii2= new Ingrediente("Carne",1.2);
        ii3= new Ingrediente("Chorizo",0.5);
        i4 = new Ingrediente("Champiñiones",1.7);
        i5 = new Ingrediente("Queso de Cabra",2);
        i7 = new Ingrediente("Pimiento",0.5);
        i8 = new Ingrediente("Pepperoni",0.7);
        i9 = new Ingrediente("4 Quesos",2.1);
        i10 = new Ingrediente("Salchicha",1);


        //INSERTADO EN LA BD
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //INSERT MENU
                r.insert(m);

                //LOS INGREDIENTES
                r.insert(ii1);
                r.insert(ii2);
                r.insert(ii3);
                r.insert(i4);
                r.insert(i5);
                r.insert(i10);
                r.insert(i7);
                r.insert(i8);
                r.insert(i9);
            }
        });
    }

    public static RealmConnector Builder(Context c)
    {
        if(rc==null)
            return new RealmConnector(c);
        else
            return rc;
    }

    public Menu cargarMenu()
    {
        return r.where(Menu.class).findFirst();
    }

    public ArrayList<Ingrediente> cargarIngredientes()
    {
        ArrayList<Ingrediente> result = new ArrayList<>();
        RealmResults<Ingrediente> ingredientes = r.where(Ingrediente.class).findAll();
        result.addAll(ingredientes);
        return result;
    }

    public Usuario obtenerUsuario(final String user, final String pass)
    {
        final Usuario[] u = new Usuario[1];
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                u[0] = r.where(Usuario.class).equalTo("user",user).and().equalTo("pass",pass).findFirst();
            }
        });
        return u[0];
    }

    public void createOrUpdateUsuario(final Usuario u)
    {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                r.copyToRealmOrUpdate(u);
            }
        });
    }

    public void deleteUsuario(final Usuario u)
    {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Usuario.deleteFromRealm(u);
            }
        });
    }

    public void realizarPedido(final Pedido p)
    {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                r.insert(p);
            }
        });
    }

    public void saveUltimoPedido(final Pedido p)
    {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                r.copyToRealmOrUpdate(p);
            }
        });
    }
}

