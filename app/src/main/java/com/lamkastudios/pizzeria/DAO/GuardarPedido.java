package com.lamkastudios.pizzeria.DAO;

import android.os.AsyncTask;

import com.lamkastudios.pizzeria.Modelo.Pedido;
import com.lamkastudios.pizzeria.Modelo.Pizza;
import com.lamkastudios.pizzeria.Modelo.Usuario;
import com.lamkastudios.pizzeria.vista.MainActivity;

import java.util.ArrayList;

import io.realm.RealmList;

//Created by chillaso All rights reserved.


public class GuardarPedido extends AsyncTask<Void,Integer,Boolean> {

    private ArrayList<Pizza> pizzas;

    public GuardarPedido(ArrayList<Pizza> pizzas) {
        this.pizzas=pizzas;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        RealmList<Pizza> pizzasRealm = new RealmList<>();
        pizzasRealm.addAll(pizzas);
        Usuario user = MainActivity.usuarioActual;
        Pedido p = new Pedido(pizzasRealm,user);
        MainActivity.rc.realizarPedido(p);
        return true;
    }
}
