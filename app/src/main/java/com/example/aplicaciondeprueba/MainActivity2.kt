package com.example.aplicaciondeprueba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var textViewEmail: TextView
    private lateinit var textViewPassword: TextView
    private lateinit var textViewRUT: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var textViewAddress: TextView

    //el modificador private se utiliza para restringir la visibilidad de una propiedad o función. Al usar private, limitas el acceso solo a la clase
    // el archivo en el que se declara, evitando que otras clases o partes del código puedan acceder directamente.

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRUT: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextFullName: EditText
    private lateinit var editTextAddress: EditText

    //El modificador lateinit en Kotlin indica que una variable será inicializada más adelante (es decir, después de su declaración) pero antes de ser utilizada.
    //Solo se puede utilizar con variables var (variables mutables) y que no tengan un valor nulo. No puede usarse con variables val (inmutables) ni con tipos primitivos (Int, Float, etc.).

    private var isEditing = false // Para saber si está en modo edición

    //En Kotlin, var se utiliza para declarar variables mutables, es decir, aquellas cuyo valor puede cambiar después de su inicialización.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)

        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPassword = findViewById(R.id.textViewPassword)
        textViewRUT = findViewById(R.id.textViewRUT)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewFullName = findViewById(R.id.textViewFullName)
        textViewAddress = findViewById(R.id.textViewAddress)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRUT = findViewById(R.id.editTextRUT)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextAddress = findViewById(R.id.editTextAddress)

        // Ocultar los EditText inicialmente
        toggleEditMode(false)

        //La idea detrás de toggleEditMode es que la misma función puede "alternar" entre estos dos modos. Aquí te explico cómo funciona cada modo:
        //
        // 1* Modo de visualización: En este modo, los datos se muestran, pero no se pueden modificar. Los TextView suelen ser elementos de solo lectura en este caso.
        //
        // 2* Modo de edición: En este modo, los datos que se mostraban anteriormente se pueden editar. Aquí, los elementos como TextView a menudo se cambian por EditText para
        // permitir que el usuario ingrese nueva información.

        val intent = intent

        textViewEmail.text = intent.getStringExtra("EXTRA_TEXTO")
        textViewPassword.text = intent.getStringExtra("EXTRA_TEXTO2")
        textViewRUT.text = intent.getStringExtra("EXTRA_TEXTO3")
        textViewPhone.text = intent.getStringExtra("EXTRA_TEXTO4")
        textViewFullName.text = intent.getStringExtra("EXTRA_TEXTO5")
        textViewAddress.text = intent.getStringExtra("EXTRA_TEXTO6")

        buttonEdit.setOnClickListener {
            isEditing = !isEditing
            if (isEditing) {
                // Cambiar a modo edición
                toggleEditMode(true)
            } else {
                // Guardar cambios y volver a modo de solo lectura
                saveChanges()
                toggleEditMode(false)
            }
        }

        // Aquí se está utilizando la variable booleana isEditing para alternar entre dos estados: modo de edición y modo de solo lectura.

        // Si isEditing es true, se cambia a false.
        // Si isEditing es false, se cambia a true.

        buttonBack.setOnClickListener {
            finish() // O vuelve a la actividad anterior
        }
        // Añadir el TextWatcher para validar el RUT
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
    }

    // Cambiar entre modo edición y solo lectura
    private fun toggleEditMode(enable: Boolean) {
        if (enable) {
            // Mostrar EditText y ocultar TextView
            textViewEmail.visibility = TextView.GONE
            textViewPassword.visibility = TextView.GONE
            textViewRUT.visibility = TextView.GONE
            textViewPhone.visibility = TextView.GONE
            textViewFullName.visibility = TextView.GONE
            textViewAddress.visibility = TextView.GONE

            editTextEmail.visibility = EditText.VISIBLE
            editTextPassword.visibility = EditText.VISIBLE
            editTextRUT.visibility = EditText.VISIBLE
            editTextPhone.visibility = EditText.VISIBLE
            editTextFullName.visibility = EditText.VISIBLE
            editTextAddress.visibility = EditText.VISIBLE

            // Pasar los valores actuales de TextView a los EditText
            editTextEmail.setText(textViewEmail.text.toString())
            editTextPassword.setText(textViewPassword.text.toString())
            editTextRUT.setText(textViewRUT.text.toString())
            editTextPhone.setText(textViewPhone.text.toString())
            editTextFullName.setText(textViewFullName.text.toString())
            editTextAddress.setText(textViewAddress.text.toString())

        } else {
            // Ocultar EditText y mostrar TextView
            textViewEmail.visibility = TextView.VISIBLE
            textViewPassword.visibility = TextView.VISIBLE
            textViewRUT.visibility = TextView.VISIBLE
            textViewPhone.visibility = TextView.VISIBLE
            textViewFullName.visibility = TextView.VISIBLE
            textViewAddress.visibility = TextView.VISIBLE

            editTextEmail.visibility = EditText.GONE
            editTextPassword.visibility = EditText.GONE
            editTextRUT.visibility = EditText.GONE
            editTextPhone.visibility = EditText.GONE
            editTextFullName.visibility = EditText.GONE
            editTextAddress.visibility = EditText.GONE
        }
    }

    // Guardar los cambios hechos en los EditText
    private fun saveChanges() {
        textViewEmail.text = editTextEmail.text.toString()
        textViewPassword.text = editTextPassword.text.toString()
        textViewRUT.text = editTextRUT.text.toString()
        textViewPhone.text = editTextPhone.text.toString()
        textViewFullName.text = editTextFullName.text.toString()
        textViewAddress.text = editTextAddress.text.toString()
    }
}
