package br.ufc.contactlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador para exibir a lista de contatos em um RecyclerView
class ContactAdapter(
    private var contacts: List<Contact>, // Lista de contatos a ser exibida
    private val onItemClicked: (Contact) -> Unit, // Callback para o clique curto em um item
    private val onItemLongClicked: (Contact) -> Unit // Callback para o clique longo em um item
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // ViewHolder que mantém referências às views de cada item
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.contactName) // Nome do contato
        val numberTextView: TextView = itemView.findViewById(R.id.contactNumber) // Número do contato
        val emailTextView: TextView = itemView.findViewById(R.id.contactEmail) // Email do contato
    }

    // Infla o layout do item e cria o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    // Vincula os dados ao ViewHolder para cada item
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name // Define o nome do contato
        holder.numberTextView.text = contact.number // Define o número do contato
        holder.emailTextView.text = contact.email // Define o email do contato

        // Define um ouvinte de clique para o item
        holder.itemView.setOnClickListener {
            onItemClicked(contact) // Chama o callback de clique curto
        }

        // Define um ouvinte de clique longo para o item
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(contact) // Chama o callback de clique longo
            true // Retorna true para indicar que o evento foi consumido
        }
    }

    // Atualiza a lista de contatos e notifica o RecyclerView para atualizar a visualização
    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    // Retorna o número total de itens no dataset
    override fun getItemCount(): Int = contacts.size
}
