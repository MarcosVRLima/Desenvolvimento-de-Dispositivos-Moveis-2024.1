package br.ufc.contactlistapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Classe que gerencia a criação e manipulação do banco de dados SQLite para contatos
class ContactDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Criação da tabela de contatos no banco de dados
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_CONTACTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID único do contato
                "$COLUMN_NAME TEXT, " + // Nome do contato
                "$COLUMN_NUMBER TEXT, " + // Número do contato
                "$COLUMN_EMAIL TEXT, " + // Email do contato
                "$COLUMN_PHOTO TEXT" + // Foto do contato (opcional)
                ")")
        db.execSQL(createTable)
    }

    // Atualiza o banco de dados quando a versão muda
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS") // Remove a tabela antiga
        onCreate(db) // Cria a tabela novamente
    }

    companion object {
        private const val DATABASE_NAME = "contacts.db" // Nome do banco de dados
        private const val DATABASE_VERSION = 1 // Versão do banco de dados
        const val TABLE_CONTACTS = "contacts" // Nome da tabela de contatos
        const val COLUMN_ID = "id" // Nome da coluna ID
        const val COLUMN_NAME = "name" // Nome da coluna Nome
        const val COLUMN_NUMBER = "number" // Nome da coluna Número
        const val COLUMN_EMAIL = "email" // Nome da coluna Email
        const val COLUMN_PHOTO = "photo" // Nome da coluna Foto
    }

    // Adiciona um novo contato ao banco de dados
    fun addContact(contact: Contact): Long {
        val db = this.writableDatabase // Obtém uma instância gravável do banco de dados
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_NUMBER, contact.number)
            put(COLUMN_EMAIL, contact.email)
            put(COLUMN_PHOTO, contact.photo)
        }
        val result = db.insert(TABLE_CONTACTS, null, values) // Insere os valores na tabela
        db.close() // Fecha a conexão com o banco de dados
        return result // Retorna o ID do novo contato
    }

    // Atualiza um contato existente
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase // Obtém uma instância gravável do banco de dados
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_NUMBER, contact.number)
            put(COLUMN_EMAIL, contact.email)
            put(COLUMN_PHOTO, contact.photo)
        }
        val result = db.update(
            TABLE_CONTACTS,
            values,
            "$COLUMN_ID=?",
            arrayOf(contact.id.toString())
        )
        db.close() // Fecha a conexão com o banco de dados
        return result // Retorna o número de linhas atualizadas
    }

    // Deleta um contato pelo ID
    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase // Obtém uma instância gravável do banco de dados
        Log.d("ContactDatabaseHelper", "Tentando deletar o contato com ID: $id")
        val result = db.delete(
            TABLE_CONTACTS,
            "$COLUMN_ID=?",
            arrayOf(id.toString())
        )
        db.close() // Fecha a conexão com o banco de dados
        Log.d("ContactDatabaseHelper", "Número de linhas deletadas: $result")
        return result // Retorna o número de linhas deletadas
    }

    // Obtém todos os contatos do banco de dados
    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = this.readableDatabase // Obtém uma instância somente leitura do banco de dados
        val cursor = db.query(
            TABLE_CONTACTS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_NUMBER, COLUMN_EMAIL, COLUMN_PHOTO),
            null, null, null, null, null
        )

        if (cursor != null) {
            try {
                // Verifica se o cursor está na primeira posição
                if (cursor.moveToFirst()) {
                    // Obtém os índices das colunas
                    val idIndex = cursor.getColumnIndex(COLUMN_ID)
                    val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                    val numberIndex = cursor.getColumnIndex(COLUMN_NUMBER)
                    val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
                    val photoIndex = cursor.getColumnIndex(COLUMN_PHOTO)

                    // Cria um objeto Contact para cada linha do cursor
                    if (idIndex != -1 && nameIndex != -1 && numberIndex != -1 && emailIndex != -1 && photoIndex != -1) {
                        do {
                            val contact = Contact(
                                cursor.getInt(idIndex),
                                cursor.getString(nameIndex),
                                cursor.getString(numberIndex),
                                cursor.getString(emailIndex),
                                cursor.getString(photoIndex)
                            )
                            contacts.add(contact)
                        } while (cursor.moveToNext())
                    } else {
                        // Registra um erro se algum índice for inválido
                        Log.e("ContactDatabaseHelper", "One or more column indices are invalid.")
                    }
                }
            } finally {
                cursor.close() // Fecha o cursor após o uso
            }
        }
        db.close() // Fecha a conexão com o banco de dados
        return contacts // Retorna a lista de contatos
    }
}
