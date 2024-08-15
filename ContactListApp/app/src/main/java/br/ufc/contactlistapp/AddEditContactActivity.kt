package br.ufc.contactlistapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import br.ufc.contactlistapp.Contact as Contact

class AddEditContactActivity : AppCompatActivity() {

    // Referências para os campos de entrada e o botão de salvar
    private lateinit var nameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        // Inicializa os campos de entrada e o botão a partir do layout
        nameEditText = findViewById(R.id.editTextName)
        numberEditText = findViewById(R.id.editTextNumber)
        emailEditText = findViewById(R.id.editTextEmail)
        saveButton = findViewById(R.id.buttonSave)

        // Recupera o contato passado pela MainActivity, se existir
        val contact = intent.getParcelableExtra<Contact>("contact")

        // Se um contato for passado, preenche os campos de entrada com seus dados
        contact?.let {
            nameEditText.setText(it.name)
            numberEditText.setText(it.number)
            emailEditText.setText(it.email)
        }

        // Configura o botão de salvar
        saveButton.setOnClickListener {
            // Obtém os valores dos campos de entrada e remove espaços extras
            val name = nameEditText.text.toString().trim()
            val number = numberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            // Validações dos campos de entrada
            if (name.isEmpty()) {
                nameEditText.error = "O nome é obrigatório"
                nameEditText.requestFocus()
                return@setOnClickListener
            }

            if (number.isEmpty()) {
                numberEditText.error = "O número é obrigatório"
                numberEditText.requestFocus()
                return@setOnClickListener
            }

            if (!isValidPhoneNumber(number)) {
                numberEditText.error = "Formato de número inválido"
                numberEditText.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty() || !isValidEmail(email)) {
                emailEditText.error = "Formato de email inválido"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            // Cria um novo contato com base nos dados inseridos
            val newContact = Contact(
                id = contact?.id ?: 0, // Mantém o ID existente ou gera um novo
                name = name,
                number = number,
                email = email,
                photo = contact?.photo // Mantém a foto existente se houver
            )

            // Devolve o contato para a MainActivity
            val resultIntent = Intent().apply {
                putExtra("newContact", newContact)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    // Valida um número de telefone (exemplo simples: apenas dígitos, com 8 a 15 caracteres)
    private fun isValidPhoneNumber(number: String): Boolean {
        return number.matches(Regex("\\d{8,15}"))
    }

    // Valida um email (utiliza o padrão de email do Android)
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
