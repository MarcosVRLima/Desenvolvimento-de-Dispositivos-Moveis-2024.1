package br.ufc.contactlistapp

import android.os.Parcel
import android.os.Parcelable

// Classe Contact que implementa Parcelable para permitir a passagem de objetos entre atividades
class Contact (
    var id: Int, // ID do contato
    var name: String, // Nome do contato
    var number: String, // Número do contato
    var email: String, // Email do contato
    var photo: String? = null // Foto do contato (opcional)
) : Parcelable {

    // Construtor primário que inicializa os atributos
    constructor(parcel: Parcel) : this(
        parcel.readInt(), // Lê o ID
        parcel.readString() ?: "", // Lê o nome (ou uma string vazia se nulo)
        parcel.readString() ?: "", // Lê o número (ou uma string vazia se nulo)
        parcel.readString() ?: ""  // Lê o email (ou uma string vazia se nulo)
    )

    // Método para escrever os dados do contato para o Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id) // Escreve o ID
        parcel.writeString(name) // Escreve o nome
        parcel.writeString(number) // Escreve o número
        parcel.writeString(email) // Escreve o email
    }

    // Método que retorna o código de descrição do conteúdo (geralmente 0 para objetos simples)
    override fun describeContents(): Int {
        return 0
    }

    // Companion object que fornece o criador Parcelable
    companion object CREATOR : Parcelable.Creator<Contact> {
        // Método para criar uma instância de Contact a partir de um Parcel
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        // Método para criar um array de Contact com tamanho especificado
        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
