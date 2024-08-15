package br.ufc.contactlistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Adapter para exibir os contatos na RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    // Lista de contatos carregada do banco de dados
    private val contacts = mutableListOf<Contact>()
    // Helper para manipulação do banco de dados
    private lateinit var dbHelper: ContactDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa a RecyclerView, o botão de adicionar contato e a SearchView
        val recyclerView: RecyclerView = findViewById(R.id.contactRecyclerView)
        val addContactButton: Button = findViewById(R.id.addContactButton)
        val searchView: SearchView = findViewById(R.id.searchView)

        // Configura o layout da RecyclerView e o adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(contacts,
            onItemClicked = { contact ->
                // Abre a AddEditContactActivity para edição de contato
                val intent = Intent(this, AddEditContactActivity::class.java)
                intent.putExtra("contact", contact)
                startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE)
            },
            onItemLongClicked = { contact ->
                // Abre a DeleteContactActivity para exclusão de contato
                val intent = Intent(this, DeleteContactActivity::class.java)
                intent.putExtra("contactId", contact.id)
                startActivityForResult(intent, DELETE_CONTACT_REQUEST_CODE)
            }
        )
        recyclerView.adapter = contactAdapter

        // Inicializa o helper do banco de dados e carrega os contatos
        dbHelper = ContactDatabaseHelper(this)
        loadContacts()

        // Configura o botão de adicionar contato
        addContactButton.setOnClickListener {
            val intent = Intent(this, AddEditContactActivity::class.java)
            startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE)
        }

        // Configura a SearchView para filtrar os contatos
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterContacts(newText.orEmpty())
                return true
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                ADD_CONTACT_REQUEST_CODE -> {
                    // Atualiza a lista de contatos após adicionar um novo
                    loadContacts()
                    val newContact = data.getParcelableExtra<Contact>("newContact")
                    if (newContact != null) {
                        val result = dbHelper.addContact(newContact)
                        if (result != -1L) {
                            newContact.id = result.toInt()
                            contacts.add(newContact)
                            contactAdapter.notifyItemInserted(contacts.size - 1)
                        }
                    }
                }
                EDIT_CONTACT_REQUEST_CODE -> {
                    // Atualiza a lista de contatos após editar um existente
                    loadContacts()
                    val updatedContact = data.getParcelableExtra<Contact>("newContact")
                    if (updatedContact != null) {
                        val result = dbHelper.updateContact(updatedContact)
                        if (result > 0) {
                            val index = contacts.indexOfFirst { it.id == updatedContact.id }
                            if (index != -1) {
                                contacts[index] = updatedContact
                                contactAdapter.notifyItemChanged(index)
                            }
                        }
                    }
                }
                DELETE_CONTACT_REQUEST_CODE -> {
                    // Remove o contato da lista após a exclusão
                    val deletedContactId = data.getIntExtra("deletedContactId", -1)
                    Log.d("MainActivity", "Contato deletado com ID: $deletedContactId")
                    if (deletedContactId != -1) {
                        val index = contacts.indexOfFirst { it.id == deletedContactId }
                        if (index != -1) {
                            contacts.removeAt(index)
                            contactAdapter.notifyItemRemoved(index)
                        }
                    }
                }
            }
        }
    }

    // Carrega todos os contatos do banco de dados e atualiza o adapter
    private fun loadContacts() {
        contacts.clear() // Limpa a lista atual
        contacts.addAll(dbHelper.getAllContacts()) // Adiciona todos os contatos do banco de dados
        contactAdapter.notifyDataSetChanged() // Notifica o adapter para atualizar a UI
    }

    // Filtra os contatos com base na consulta da SearchView
    private fun filterContacts(query: String) {
        val filteredContacts = contacts.filter { contact ->
            contact.name.contains(query, ignoreCase = true) ||
                    contact.number.contains(query, ignoreCase = true) ||
                    contact.email.contains(query, ignoreCase = true)
        }
        contactAdapter.updateContacts(filteredContacts) // Atualiza a lista de contatos exibida
    }

    companion object {
        private const val ADD_CONTACT_REQUEST_CODE = 1
        private const val EDIT_CONTACT_REQUEST_CODE = 2
        private const val DELETE_CONTACT_REQUEST_CODE = 3
    }
}
