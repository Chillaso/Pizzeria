package com.lamkastudios.pizzeria;

/**
 * Created by ardark on 11/7/17.
 */

public class IncompatibilidadException extends Exception {

    private String mensaje;

    IncompatibilidadException()
    {
        mensaje="Ingredientes incompatibles";
    }

    IncompatibilidadException(String mensaje)
    {
        this.mensaje=mensaje;
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
