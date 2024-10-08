package com.example.aplicaciondeprueba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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

    // Agrega la referencia a la base de datos de Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().getReference("users")

        // Referencias a los botones
        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonDelete: Button = findViewById(R.id.buttonDelete) // Botón de eliminar
        val buttonNext: Button = findViewById(R.id.buttonNext) // Botón siguiente

        // Referencias a los TextView para mostrar la información
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPassword = findViewById(R.id.textViewPassword)
        textViewRUT = findViewById(R.id.textViewRUT)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewFullName = findViewById(R.id.textViewFullName)
        textViewAddress = findViewById(R.id.textViewAddress)

        // Referencias a los EditText para editar la información
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRUT = findViewById(R.id.editTextRUT)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextAddress = findViewById(R.id.editTextAddress)

        // Habilitar o deshabilitar el modo de edición
        toggleEditMode(false)

        val intent = intent
        val userId = intent.getStringExtra("USER_ID") // Recibe el USER_ID

        // Asignar los datos recibidos
        textViewEmail.text = intent.getStringExtra("EXTRA_TEXTO")
        textViewPassword.text = intent.getStringExtra("EXTRA_TEXTO2")
        textViewRUT.text = intent.getStringExtra("EXTRA_TEXTO3")
        textViewPhone.text = intent.getStringExtra("EXTRA_TEXTO4")
        textViewFullName.text = intent.getStringExtra("EXTRA_TEXTO5")
        textViewAddress.text = intent.getStringExtra("EXTRA_TEXTO6")

        buttonEdit.setOnClickListener {
            isEditing = !isEditing
            toggleEditMode(isEditing)
        }

        buttonBack.setOnClickListener {
            intent.putExtra("CLEAR_FIELDS", true)
            setResult(RESULT_OK, intent)
            finish()
        }

        buttonSave.setOnClickListener {
            saveChanges(userId) // Guardar cambios en Firebase
        }

        buttonDelete.setOnClickListener {
            deleteData(userId, buttonSave, buttonEdit, buttonDelete)
        }

        // Botón siguiente
        buttonNext.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
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

    // Método para alternar entre el modo de edición y visualización
    private fun toggleEditMode(enable: Boolean) {
        if (enable) {
            // Ocultar los TextView y mostrar los EditText
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

            // Pasar el texto de los TextView a los EditText
            editTextEmail.setText(textViewEmail.text.toString())
            editTextPassword.setText(textViewPassword.text.toString())
            editTextRUT.setText(textViewRUT.text.toString())
            editTextPhone.setText(textViewPhone.text.toString())
            editTextFullName.setText(textViewFullName.text.toString())
            editTextAddress.setText(textViewAddress.text.toString())

        } else {
            // Mostrar los TextView y ocultar los EditText
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

    private fun saveChanges(userId: String?) {
        textViewEmail.text = editTextEmail.text.toString()
        textViewPassword.text = editTextPassword.text.toString()
        textViewRUT.text = editTextRUT.text.toString()
        textViewPhone.text = editTextPhone.text.toString()
        textViewFullName.text = editTextFullName.text.toString()
        textViewAddress.text = editTextAddress.text.toString()

        // Verificar si el userId es válido
        if (!userId.isNullOrEmpty()) { // Verifica que userId no sea nulo o vacío
            // Crear un mapa con los datos actualizados
            val updatedUserData = mapOf(
                "email" to textViewEmail.text.toString(),
                "password" to textViewPassword.text.toString(),
                "rut" to textViewRUT.text.toString(),
                "phone" to textViewPhone.text.toString(),
                "fullName" to textViewFullName.text.toString(),
                "address" to textViewAddress.text.toString()
            )

            // Actualizar los datos en Firebase
            database.child(userId).updateChildren(updatedUserData).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "No se encontró el ID del usuario", Toast.LENGTH_SHORT).show()
        }
    }

    // Borrar los datos y deshabilitar botones
    private fun deleteData(
        userId: String?,
        buttonSave: Button,
        buttonEdit: Button,
        buttonDelete: Button
    ) {

        // Limpiar los EditText
        editTextEmail.setText("")
        editTextPassword.setText("")
        editTextRUT.setText("")
        editTextPhone.setText("")
        editTextFullName.setText("")
        editTextAddress.setText("")

        // Limpiar los TextViews
        textViewEmail.text = ""
        textViewPassword.text = ""
        textViewRUT.text = ""
        textViewPhone.text = ""
        textViewFullName.text = ""
        textViewAddress.text = ""

        // Deshabilitar botones de guardar y editar
        buttonSave.isEnabled = false
        buttonEdit.isEnabled = false
        buttonDelete.isEnabled = false

        // Borrar los datos de Firebase si el userId es válido
        if (userId != null) {
            database.child(userId).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Información eliminada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al eliminar la información", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "No se encontró el ID del usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
