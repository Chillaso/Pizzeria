package com.lamkastudios.pizzeria

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import com.lamkastudios.pizzeria.POJOS.Ingrediente
import com.lamkastudios.pizzeria.POJOS.Pedido
import com.lamkastudios.pizzeria.POJOS.Pizza
import kotlinx.android.synthetic.main.activity_pedido.*

class PedidoActivity : AppCompatActivity(), AdapterView.OnItemClickListener{

    lateinit var nombre: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        if(intent.extras.getBoolean("repetir",false))
        {
            val nombre = getSharedPreferences("datos",0).getString("nombre","")
            val masa = getSharedPreferences("datos",0).getString("masa","")
            val tam =getSharedPreferences("datos",0).getString("tam","")
            val precio = getSharedPreferences("datos",0).getFloat("precio",0f)

            val p :Pizza = Pizza(nombre,masa,tam,precio.toDouble(), ArrayList<Ingrediente>())
            Pedido.ultPizza.add(p)
            Pedido.pizzas.add(p)
            CargarDatos(Pedido.ultPizza)
        }
        else {
            CargarDatos(Pedido.pizzas)
        }

        //LISTENERS
        btnPagar.setOnClickListener({startActivity(Intent(this, PagoActivity::class.java))})
        btnCancelar.setOnClickListener({
            //Reiniciamos
            Pedido.pizzas.removeAll(Pedido.pizzas)
            startActivity(Intent(this,MainActivity::class.java))})

        listaPizzas.onItemClickListener=this
    }

    //EVENTO LISTA
    override fun onItemClick(p0: AdapterView<*>?, item: View, pos: Int, p3: Long) {
        for(pizza in Pedido.pizzas)
        {
            if(nombre.get(pos).equals(pizza.name))
            {
                val precio = pizza.precio
                Math.abs(precio)
                precioPizza.text=precio.toString()
            }
        }
    }

    //EXTENSIÓN DE TOAST
    private fun AppCompatActivity.toast(mensaje: String)
    {
        android.widget.Toast.makeText(this,mensaje, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun CargarDatos(lista: ArrayList<Pizza>)
    {
        //DATOS LISTA
        nombre=ArrayList()
        var calc = 0.0
        for(pizza in lista)
        {
            nombre.add(pizza.name)
            calc+=pizza.precio
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.texto_spinner,nombre)
        listaPizzas.adapter= adapter

        //PRECIO TOTAL
        Math.abs(calc)
        precioTotal.text=calc.toString()
    }
}
