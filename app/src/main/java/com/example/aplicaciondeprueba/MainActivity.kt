package com.example.aplicaciondeprueba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val editTextRUT: EditText = findViewById(R.id.editTextRUT)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextFullName: EditText = findViewById(R.id.editTextFullName)
        val editTextAddress: EditText = findViewById(R.id.editTextAddress)

        // Listener para validar el RUT
        editTextRUT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val userInput = s.toString()
                val rutPattern = Regex("^[0-9]{7,8}-[0-9kK]$")
                if (userInput.length !in 9..10) {
                    editTextRUT.error = "RUT no válido (Debe contener - )."
                } else if (!userInput.matches(rutPattern)) {
                    editTextRUT.error = "El RUT no tiene el formato correcto."
                } else {
                    editTextRUT.error = null
                }
            }
        })

        buttonRegister.setOnClickListener {
            val correoIngresado = editTextEmail.text.toString()
            val passwordIngresado = editTextPassword.text.toString()
            val rutIngresado = editTextRUT.text.toString()
            val phoneIngresado = editTextPhone.text.toString()
            val fullNameIngresado = editTextFullName.text.toString()
            val addressIngresado = editTextAddress.text.toString()

            if (correoIngresado.isEmpty() || passwordIngresado.isEmpty() || rutIngresado.isEmpty() || phoneIngresado.isEmpty() || fullNameIngresado.isEmpty() || addressIngresado.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("EXTRA_TEXTO", correoIngresado)
                intent.putExtra("EXTRA_TEXTO2", passwordIngresado)
                intent.putExtra("EXTRA_TEXTO3", rutIngresado)
                intent.putExtra("EXTRA_TEXTO4", phoneIngresado)
                intent.putExtra("EXTRA_TEXTO5", fullNameIngresado)
                intent.putExtra("EXTRA_TEXTO6", addressIngresado)
                startActivity(intent)
            }
        }
    }
}
