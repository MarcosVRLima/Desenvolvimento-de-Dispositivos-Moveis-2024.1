package br.ufc.contactlistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// Atividade responsável por deletar um contato
class DeleteContactActivity : AppCompatActivity() {

    private lateinit var deleteButton: Button // Botão para confirmar a exclusão
    private lateinit var cancelButton: Button // Botão para cancelar a exclusão
    private lateinit var dbHelper: ContactDatabaseHelper // Ajuda na manipulação do banco de dados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_contact) // Define o layout da atividade

        // Inicializa os botões e o helper do banco de dados
        deleteButton = findViewById(R.id.confirmDeleteButton)
        cancelButton = findViewById(R.id.cancelButton)
        dbHelper = ContactDatabaseHelper(this)

        // Recupera o ID do contato a ser deletado a partir do Intent
        val contactId = intent.getIntExtra("contactId", -1)

        // Configura o listener para o botão de deletar
        deleteButton.setOnClickListener {
            if (contactId != -1) { // Verifica se o ID do contato é válido
                // Deleta o contato do banco de dados
                val result = dbHelper.deleteContact(contactId)
                if (result > 0) { // Verifica se a exclusão foi bem-sucedida
                    Log.d("DeleteContactActivity", "Contato deletado com sucesso")
                    // Cria um Intent para retornar o ID do contato deletado
                    val resultIntent = Intent().apply {
                        putExtra("deletedContactId", contactId)
                    }
                    setResult(RESULT_OK, resultIntent) // Define o resultado da atividade
                    finish() // Finaliza a atividade
                } else {
                    Log.e("DeleteContactActivity", "Erro ao deletar o contato") // Registra um erro se a exclusão falhar
                }
            }
        }

        // Configura o listener para o botão de cancelar
        cancelButton.setOnClickListener {
            finish() // Apenas fecha a atividade sem fazer alterações
        }
    }
}
