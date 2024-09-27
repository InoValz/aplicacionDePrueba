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
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this) // Iniciar Firebase

        setContentView(R.layout.activity_main)

        // Referencia a la base de datos de Firebase
        database = FirebaseDatabase.getInstance().getReference("users")

        // Referencias a los elementos del layout
        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val editTextRUT: EditText = findViewById(R.id.editTextRUT)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextFullName: EditText = findViewById(R.id.editTextFullName)
        val editTextAddress: EditText = findViewById(R.id.editTextAddress)

        // Validación de RUT
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

        // Acción del botón de registro
        buttonRegister.setOnClickListener {
            val correoIngresado = editTextEmail.text.toString()
            val passwordIngresado = editTextPassword.text.toString()
            val rutIngresado = editTextRUT.text.toString()
            val phoneIngresado = editTextPhone.text.toString()
            val fullNameIngresado = editTextFullName.text.toString()
            val addressIngresado = editTextAddress.text.toString()

            // Validación de campos vacíos
            if (correoIngresado.isEmpty() || passwordIngresado.isEmpty() || rutIngresado.isEmpty() || phoneIngresado.isEmpty() || fullNameIngresado.isEmpty() || addressIngresado.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("EXTRA_TEXTO", correoIngresado)
                intent.putExtra("EXTRA_TEXTO2", passwordIngresado)
                intent.putExtra("EXTRA_TEXTO3", rutIngresado)
                intent.putExtra("EXTRA_TEXTO4", phoneIngresado)
                intent.putExtra("EXTRA_TEXTO5", fullNameIngresado)
                intent.putExtra("EXTRA_TEXTO6", addressIngresado)
                startActivityForResult(intent,1)

                // Guardar los datos del usuario en Firebase
                saveUserData(correoIngresado, passwordIngresado, rutIngresado, phoneIngresado, fullNameIngresado, addressIngresado) // Guardar datos en Base de Datos
            }
        }
    }

    // Método para guardar datos de usuario en Firebase
    private fun saveUserData(email: String, password: String, rut: String, phone: String, fullName: String, address: String) {
        // Generar un ID único para el usuario
        val userId = database.push().key ?: return

        // Crear un objeto User con los datos
        val user = User(userId, email, password, rut, phone, fullName, address)

        // Guardar los datos del usuario en Firebase
        database.child(userId).setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

                // Iniciar MainActivity2 pasando los datos y el userId
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("USER_ID", userId) // Pasar el ID del usuario
                intent.putExtra("EXTRA_TEXTO", email)
                intent.putExtra("EXTRA_TEXTO2", password)
                intent.putExtra("EXTRA_TEXTO3", rut)
                intent.putExtra("EXTRA_TEXTO4", phone)
                intent.putExtra("EXTRA_TEXTO5", fullName)
                intent.putExtra("EXTRA_TEXTO6", address)
                startActivityForResult(intent, 1)
            } else {
                Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para recibir el resultado de MainActivity2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Limpiar campos si se recibe la señal de MainActivity2
            if (data?.getBooleanExtra("CLEAR_FIELDS", false) == true) {
                findViewById<EditText>(R.id.editTextEmail).text.clear()
                findViewById<EditText>(R.id.editTextPassword).text.clear()
                findViewById<EditText>(R.id.editTextRUT).text.clear()
                findViewById<EditText>(R.id.editTextPhone).text.clear()
                findViewById<EditText>(R.id.editTextFullName).text.clear()
                findViewById<EditText>(R.id.editTextAddress).text.clear()
            }
        }
    }
}
