# Contact List App

## Descrição

O Contact List App é uma aplicação Android que permite aos usuários gerenciar uma lista de contatos. A aplicação oferece funcionalidades para adicionar, editar, excluir e buscar contatos, além de persistir os dados em um banco de dados SQLite.

## Funcionalidades

- **Adicionar Contatos**: Permite adicionar novos contatos com nome, número e e-mail.
- **Editar Contatos**: Permite editar os detalhes de um contato existente.
- **Excluir Contatos**: Permite excluir contatos da lista.
- **Buscar Contatos**: Permite buscar contatos por nome, número ou e-mail.
- **Listagem de Contatos**: Exibe a lista de contatos em um `RecyclerView`.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação usada para desenvolver a aplicação.
- **Android SDK**: Framework de desenvolvimento Android.
- **SQLite**: Banco de dados para persistência dos dados dos contatos.

## Estrutura do Projeto

### Atividades

- **MainActivity**: Tela principal que exibe a lista de contatos e permite a adição, edição e exclusão de contatos. Inclui uma `SearchView` para buscar contatos.
- **AddEditContactActivity**: Tela para adicionar ou editar contatos. Inclui validação de entrada para garantir dados corretos.
- **DeleteContactActivity**: Tela para confirmar a exclusão de um contato.

### Componentes

- **Contact**: Classe de modelo que representa um contato. Implementa `Parcelable` para permitir a passagem de dados entre atividades.
- **ContactAdapter**: Adaptador para exibir a lista de contatos em um `RecyclerView`.
- **ContactDatabaseHelper**: Helper para operações de banco de dados, incluindo adição, atualização, exclusão e recuperação de contatos.