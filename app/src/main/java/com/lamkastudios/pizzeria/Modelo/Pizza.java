package com.lamkastudios.pizzeria.Modelo;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Pizza extends RealmObject
{
    private String name;
    private String masa;
    private String tam;
    private double precio;
    private int descripcion;
    private RealmList<Ingrediente> ingredientes;

    public Pizza() {
    }

    public Pizza(String name, String masa, String tam, double precio, int descripcion, RealmList<Ingrediente> ingredientes) {
        this.name = name;
        this.masa = masa;
        this.tam = tam;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
    }

    public Pizza(String name, String masa, String tam, double precio, RealmList<Ingrediente> ingredientes)
    {
        this.name = name;
        this.masa = masa;
        this.tam = tam;
        this.precio = precio;
        this.ingredientes = ingredientes;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public RealmList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(RealmList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", masa='" + masa + '\'' +
                ", tam='" + tam + '\'' +
                ", precio=" + precio +
                ", ingredientes=" + ingredientes +
                '}';
    }
}
