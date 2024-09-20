package com.example.aplicaciondeprueba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var textViewEmail: TextView
    private lateinit var textViewPassword: TextView
    private lateinit var textViewRUT: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewFullName: TextView
    private lateinit var textViewAddress: TextView

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRUT: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextFullName: EditText
    private lateinit var editTextAddress: EditText

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonDelete: Button = findViewById(R.id.buttonDelete) // Botón de eliminar

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

        toggleEditMode(false)

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
                toggleEditMode(true)
            } else {
                saveChanges()
                toggleEditMode(false)
            }
        }

        buttonBack.setOnClickListener {
            val intent = Intent()
            intent.putExtra("CLEAR_FIELDS", true)
            setResult(RESULT_OK, intent)
            finish()
        }

        buttonDelete.setOnClickListener {
            deleteData(buttonSave, buttonEdit) // Función para borrar datos y deshabilitar botones
        }

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

    private fun toggleEditMode(enable: Boolean) {
        if (enable) {
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

            editTextEmail.setText(textViewEmail.text.toString())
            editTextPassword.setText(textViewPassword.text.toString())
            editTextRUT.setText(textViewRUT.text.toString())
            editTextPhone.setText(textViewPhone.text.toString())
            editTextFullName.setText(textViewFullName.text.toString())
            editTextAddress.setText(textViewAddress.text.toString())

        } else {
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

    private fun saveChanges() {
        textViewEmail.text = editTextEmail.text.toString()
        textViewPassword.text = editTextPassword.text.toString()
        textViewRUT.text = editTextRUT.text.toString()
        textViewPhone.text = editTextPhone.text.toString()
        textViewFullName.text = editTextFullName.text.toString()
        textViewAddress.text = editTextAddress.text.toString()
    }

    // Borrar los datos y deshabilitar botones
    private fun deleteData(buttonSave: Button, buttonEdit: Button) {
        editTextEmail.setText("")
        editTextPassword.setText("")
        editTextRUT.setText("")
        editTextPhone.setText("")
        editTextFullName.setText("")
        editTextAddress.setText("")

        textViewEmail.text = ""
        textViewPassword.text = ""
        textViewRUT.text = ""
        textViewPhone.text = ""
        textViewFullName.text = ""
        textViewAddress.text = ""

        // Deshabilitar botones de guardar y editar
        buttonSave.isEnabled = false
        buttonEdit.isEnabled = false

        // Mostrar mensaje de "Información eliminada"
        Toast.makeText(this, "Información eliminada", Toast.LENGTH_SHORT).show()
    }
}
