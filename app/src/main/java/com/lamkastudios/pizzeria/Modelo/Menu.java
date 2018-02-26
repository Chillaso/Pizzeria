package com.lamkastudios.pizzeria.Modelo;
//Created by chillaso All rights reserved.


import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Menu extends RealmObject{

    private RealmList<Pizza> pizzas;
    private RealmList<Integer> imagenes;

    public Menu() {
    }

    public Menu(RealmList<Pizza> pizzas, RealmList<Integer> imagenes) {
        this.pizzas = pizzas;
        this.imagenes = imagenes;
    }

    public RealmList<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(RealmList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public RealmList<Integer> getImagenes() {
        return imagenes;
    }

    public void setImagenes(RealmList<Integer> imagenes) {
        this.imagenes = imagenes;
    }
}
