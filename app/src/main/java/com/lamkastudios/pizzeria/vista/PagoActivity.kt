package com.lamkastudios.pizzeria.vista

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lamkastudios.pizzeria.Modelo.Pedido
import com.lamkastudios.pizzeria.Modelo.Pizza
import com.lamkastudios.pizzeria.R
import kotlinx.android.synthetic.main.activity_pago.*

class PagoActivity : AppCompatActivity(), OnMapReadyCallback, Runnable{

    private var map :GoogleMap? = null
    lateinit var dialog: CargandoDialog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)
        //Funcion lambda {} / Introspeccion, Reflection ::
        btnIrPago.setOnClickListener(ComprobarPago)
        mapa.onCreate(savedInstanceState)
        mapa.getMapAsync(this)

    }

    private var ComprobarPago : OnClickListener = OnClickListener()
    {
        if(grupoRadio.checkedRadioButtonId!=-1)
        {
            Snackbar.make(mapa,"Pago realizado correctamente",Snackbar.LENGTH_SHORT).show()
            val hilo : Thread = Thread(this)
            hilo.start()

            //GUARDAR PIZZA
            val pref :SharedPreferences = getSharedPreferences("datos",0)
            val editor :SharedPreferences.Editor = pref.edit()
            val p: Pizza = Pedido.pizzas.get(Pedido.pizzas.size-1)

            editor.putString("nombre",p.name)
            editor.putString("masa",p.masa)
            editor.putString("tam",p.tam)
            editor.putFloat("precio", p.precio.toFloat())
            editor.apply()
        }
        else
        {
            Toast.makeText(this,"Escoga un método de pago",Toast.LENGTH_SHORT).show()
        }
    }

    //NOTIFICACION
    fun NuevaNotificacion()
    {
        val id="canal"
        val name: CharSequence = getString(R.string.notificacion)
        val description = getString(R.string.descriptionN)

        val nm: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //Configuración de la notificacion
        val builder = NotificationCompat.Builder(this,id)
                .setContentTitle(name)
                .setStyle(NotificationCompat.BigTextStyle().bigText(description))
                .setContentText(description)
                .setSmallIcon(R.drawable.pizza)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)

        nm.notify(1,builder.build())
    }

    override fun run()
    {
        dialog= CargandoDialog()
        dialog.show(fragmentManager,"cargando")
        Thread.sleep(5000)
        dialog.dismiss()
        NuevaNotificacion()
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    //MAPA
    @SuppressLint("MissingPermission")
    override fun onMapReady(mapa: GoogleMap?)
    {
        this.map=mapa
        mapa?.uiSettings?.setAllGesturesEnabled(true)
        val jerez= LatLng(36.696067, -6.098873)
        mapa?.moveCamera(CameraUpdateFactory.newLatLngZoom(jerez,15f))
        mapa?.addMarker(MarkerOptions().position(jerez).title("Pizzeria Carlos"))
        mapa?.setOnMapLongClickListener { l: LatLng -> mapa?.addMarker(MarkerOptions().position(l).title(l.toString())) }

    }

    public override fun onResume() {
        super.onResume()
        mapa.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapa.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapa.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapa.onLowMemory()
    }
}
