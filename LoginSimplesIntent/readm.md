# Projeto de Login Simples

Este é um projeto simples de aplicativo Android desenvolvido em Kotlin usando Jetpack Compose. O aplicativo possui duas telas principais: uma tela de login e uma tela principal que exibe uma mensagem de boas-vindas ao usuário após um login bem-sucedido. O projeto também inclui uma funcionalidade de logoff que redireciona o usuário de volta à tela de login.

## Funcionalidades

- **Tela de Login**:
    - Campos para inserir nome de usuário e senha.
    - Validação de credenciais contra uma lista predefinida.
    - Exibição de uma mensagem de erro (Toast) se as credenciais forem inválidas.

- **Tela Principal**:
    - Exibe uma mensagem de boas-vindas ao usuário com base no nome de usuário fornecido durante o login.
    - Botão de logoff para retornar à tela de login e limpar a pilha de navegação.
