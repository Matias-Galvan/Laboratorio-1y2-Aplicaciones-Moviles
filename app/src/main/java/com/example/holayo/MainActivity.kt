package com.example.holayo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Tu modelo de datos: una línea, y ya tiene igualdad, copia y representación.
data class Perfil(
    val nombre: String,
    val dato: String,
    val apodo: String?,   // el ? declara: "puede no haber apodo"
    val comidaFavorita: String? = null // tercer dato opcional
)

class MainActivity : AppCompatActivity() {

    private val perfil = Perfil(
        nombre = "Matías Estudiante",
        dato = "Estoy cursando Aplicaciones Móviles",
        apodo = "Kofy",
        comidaFavorita = "Pizza" // probá cambiando a null para probar el patrón ?.let { }
    )

    private var saludoFormal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("VIDA", "Main → onCreate")
        setContentView(R.layout.activity_main)

        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)
        val tvDato = findViewById<TextView>(R.id.tvDato)
        val tvComidaFavorita = findViewById<TextView>(R.id.tvComidaFavorita)
        val btnSaludar = findViewById<Button>(R.id.btnSaludar)
        val btnIrSegunda = findViewById<Button>(R.id.btnIrSegunda)
        val btnCompartir = findViewById<Button>(R.id.btnCompartir)

        // Si hay apodo se usa; si es null, el nombre. El operador ?: es el "plan B".
        val comoLlamarme = perfil.apodo ?: perfil.nombre

        tvSaludo.text = "Hola, soy $comoLlamarme"
        tvDato.text = perfil.dato

        // Patrón ?.let { }: este bloque solo se ejecuta si comidaFavorita NO es null
        perfil.comidaFavorita?.let { comida ->
            tvComidaFavorita.text = "Comida favorita: $comida"
            tvComidaFavorita.visibility = View.VISIBLE
        }

        // Una lambda: la función que se ejecuta cuando el botón se toca.
        btnSaludar.setOnClickListener {
            saludoFormal = !saludoFormal
            tvSaludo.text = if (saludoFormal)
                "Hola, soy $comoLlamarme"
            else
                "¡Buenas! Acá $comoLlamarme"
        }

        btnIrSegunda.setOnClickListener {
            // Intent EXPLÍCITO: nombra al destino por su clase.
            val intent = Intent(this, SegundaActivity::class.java)
            // El equipaje: un dato que viaja con el mensaje.
            intent.putExtra("nombre", perfil.apodo ?: perfil.nombre)
            startActivity(intent)
        }

        btnCompartir.setOnClickListener {
            val textoACompartir = "¡Hola! Mi nombre es ${perfil.nombre} (${perfil.apodo ?: "sin apodo"}). ${perfil.dato}"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textoACompartir)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("VIDA", "Main → onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("VIDA", "Main → onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("VIDA", "Main → onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("VIDA", "Main → onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("VIDA", "Main → onDestroy")
    }
}