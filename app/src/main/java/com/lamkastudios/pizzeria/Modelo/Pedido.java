package com.lamkastudios.pizzeria.Modelo;
//Created by chillaso All rights reserved.


import io.realm.RealmList;
import io.realm.RealmObject;

public class Pedido extends RealmObject{

    private RealmList<Pizza> pizzas;
    private Usuario user;

    public Pedido() {
    }

    public Pedido(RealmList<Pizza> pizzas, Usuario user) {
        this.pizzas = pizzas;
        this.user = user;
    }

    public RealmList<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(RealmList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
