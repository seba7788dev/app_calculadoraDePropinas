package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonCalcular.setOnClickListener {
            calcularTip()
        }
        //llamo a la funcion para ocultar teclado
        binding.costOfServiceEditText.setOnKeyListener{view,keyCode, _->handleKeyEvent(view,keyCode)}
    }

    private fun calcularTip(){

        // Guardo la informacion que ingresa el usuario del costo del servicio
        val infoCostoServicio=binding.costOfServiceEditText.text.toString()
        val costo=infoCostoServicio.toDoubleOrNull()
        //evito error en caso de que el usuario ingrese un null
        if (costo==null){
            binding.resultadoTip.text = ""
            return
        }

        //Guardo la opcion elegida x el usuario
        val opcionElegida=binding.tipsOpciones.checkedRadioButtonId

        //indico el porcentaje correspondiente, dependiendo la eleccion del usuario
        val porcentajeACalcular=when(opcionElegida){
            R.id.opcion_veinte_porc -> 0.2
            R.id.opcion_diez_porc ->0.10
            else->.05
        }

        // calculo propina
        var propina=costo*porcentajeACalcular

        // Guardo la opcion seleccionada en el switch
        val redondeo=binding.opcionRedondear.isChecked

        //Redondeo si corresponde con la opcion elegida

        if(redondeo){
            propina=Math.ceil(propina)
        }
        //Paso a formato moneda el resultado de la propina
        val propinaEnPesos=NumberFormat.getCurrencyInstance().format(propina)

        //Muestro la propina -- resultado_de_propina es el nombre del recurso string

        binding.resultadoTip.text=getString(R.string.resultado_de_propina,propinaEnPesos)







    }

    // funcion para esconder el teclado cada vez q se oprime la tecla enter

    private fun handleKeyEvent(view:View,keyCode:Int):Boolean{
            if (keyCode== KeyEvent.KEYCODE_ENTER){
                //Escondo el teclado
                val inputMethodManager=
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                return true

            }
        return false
    }




}
