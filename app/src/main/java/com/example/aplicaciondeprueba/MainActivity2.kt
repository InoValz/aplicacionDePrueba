package com.example.aplicaciondeprueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        val textViewEmail: TextView = findViewById(R.id.textViewEmail)
        val textViewPassword: TextView = findViewById(R.id.textViewPassword)
        val textViewRUT: TextView = findViewById(R.id.textViewRUT)
        val textViewPhone: TextView = findViewById(R.id.textViewPhone)
        val textViewFullName: TextView = findViewById(R.id.textViewFullName)
        val textViewAddress: TextView = findViewById(R.id.textViewAddress)

        val intent = intent

            // Mostrar la información recibida
            textViewEmail.text = intent.getStringExtra("EXTRA_TEXTO")
            textViewPassword.text = intent.getStringExtra("EXTRA_TEXTO2")
            textViewRUT.text = intent.getStringExtra("EXTRA_TEXTO3")
            textViewPhone.text = intent.getStringExtra("EXTRA_TEXTO4")
            textViewFullName.text = intent.getStringExtra("EXTRA_TEXTO5")
            textViewAddress.text = intent.getStringExtra("EXTRA_TEXTO6")


        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
