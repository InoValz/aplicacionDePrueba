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
            //Un TextWatcher es una interfaz que permite escuchar los cambios de texto en un EditText.
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            //Este método se llama antes de que el texto en el campo de texto cambie. Aquí no se realiza ninguna acción, por lo que el método está vacío.
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            //Este método se llama mientras el texto está cambiando. Nuevamente, no se realiza ninguna acción específica aquí, por lo que el método está vacío.
            override fun afterTextChanged(s: Editable?) {
            //Este método se llama después de que el texto ha cambiado. Aquí es donde se realiza la validación del RUT.
                val userInput = s.toString()
                //Convierte el contenido del EditText (s) en una cadena (String) y lo almacena en la variable userInput.
                val rutPattern = Regex("^[0-9]{7,8}-[0-9kK]$")
                //Se define un patrón de expresión regular (Regex) para validar el formato del RUT. Este patrón acepta:
                //    7 u 8 dígitos seguidos por un guion (-)
                //    Un dígito o la letra k o K al final.
                if (userInput.length !in 9..10) {
                    //Verifica si la longitud del userInput no está entre 9 y 10 caracteres. Los RUT válidos en Chile deben tener entre 9 y 10 caracteres
                    // (considerando el guion y el dígito verificador).
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
                //"isEmpty" se utiliza para verificar si una cadena (String) está vacía, es decir, si no contiene ningún carácter.
                //Si alguno de los campos está vacío, se ejecutará el bloque de código dentro del if, mostrando un mensaje (usando un Toast) que indica que se deben completar todos los campos.
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
