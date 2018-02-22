package com.lamkastudios.pizzeria.Modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class Pizza
{
    private String name;
    private String masa;
    private String tam;
    private double precio;
    private ArrayList<Ingrediente> ingredientes;

    public Pizza(String name, String masa, String tam, double precio, ArrayList<Ingrediente> ingredientes)
    {
        this.name = name;
        this.masa = masa;
        this.tam = tam;
        this.precio = precio;
        this.ingredientes = ingredientes;
    }

    public Pizza(String name, String masa, String tam, double precio, Ingrediente[] ingredientes)
    {
        this.name = name;
        this.masa = masa;
        this.tam = tam;
        this.precio = precio;

        this.ingredientes=new ArrayList<>();
        this.ingredientes.addAll(Arrays.asList(ingredientes));
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

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
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
