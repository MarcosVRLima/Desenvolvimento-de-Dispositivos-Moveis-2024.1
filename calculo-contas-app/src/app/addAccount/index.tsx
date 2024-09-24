import React, { useContext, useState } from "react";
import {
  View,
  TextInput,
  Button,
  Text,
  FlatList,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import { db } from "./../../../firebaseConfig"; // Importando a configuração do Firebase
import { collection, addDoc } from "firebase/firestore";
import { router } from "expo-router";
import AuthContext from "@/context/authContext";

const AddAccountScreen = () => {
  const [nomeConta, setNomeConta] = useState("");
  const [valorTotal, setValorTotal] = useState("");
  const [pessoasTotais, setPessoasTotais] = useState("");
  const [pessoas, setPessoas] = useState([]);
  const [geracaoConcluida, setGeracaoConcluida] = useState(false); // Novo estado para controlar a exibição
  const { user } = useContext(AuthContext);

  // Função para gerar a distribuição inicial de valores
  const gerarDistribuicaoInicial = () => {
    const totalPessoas = parseInt(pessoasTotais);
    const valorPorPessoa = parseFloat(valorTotal) / totalPessoas;

    const novaDistribuicao = [];
    for (let i = 0; i < totalPessoas; i++) {
      novaDistribuicao.push({
        pessoa: `Pessoa ${i + 1}`,
        valor: valorPorPessoa.toFixed(2),
        travado: false,
      });
    }
    setPessoas(novaDistribuicao);
    setGeracaoConcluida(true); // Define como true após gerar a distribuição
  };

  // Função para editar o nome de uma pessoa
  const editarNomePessoa = (index, novoNome) => {
    const novasPessoas = [...pessoas];
    novasPessoas[index].pessoa = novoNome;
    setPessoas(novasPessoas);
  };

  // Função para alterar o valor de uma pessoa e travar seu valor
  const editarValorPessoa = (index, novoValor) => {
    const novasPessoas = [...pessoas];
    novasPessoas[index].valor = novoValor;
    novasPessoas[index].travado = true; // Travar o valor dessa pessoa

    const valorRestante =
      parseFloat(valorTotal) -
      novasPessoas
        .filter((p) => p.travado)
        .reduce((acc, p) => acc + parseFloat(p.valor), 0);

    const pessoasNaoTravadas = novasPessoas.filter((p) => !p.travado);

    const novoValorPorPessoa = valorRestante / pessoasNaoTravadas.length;

    pessoasNaoTravadas.forEach((pessoa) => {
      pessoa.valor = novoValorPorPessoa.toFixed(2);
    });

    setPessoas(novasPessoas);
  };

  // Função para salvar a conta no Firebase
  const salvarConta = async () => {
    const valores = pessoas.map((pessoa) => ({
      pessoa: pessoa.pessoa,
      valor: pessoa.valor,
    }));

    try {
      const docRef = await addDoc(collection(db, "contas"), {
        valortotal: valorTotal,
        pessoastotais: pessoasTotais,
        nomeConta: nomeConta,
        idusuario: user.id, // Substituir pelo ID do usuário autenticado
        valores: valores,
      });
      console.log("Conta adicionada com ID: ", docRef.id);
      router.push("home"); // Volta para a tela inicial
    } catch (e) {
      console.error("Erro ao adicionar conta: ", e);
    }
  };

  return (
    <View style={styles.container}>
      {!geracaoConcluida ? (
        <>
          <Text style={styles.label}>Nome da Conta:</Text>
          <TextInput
            style={styles.input}
            value={nomeConta}
            onChangeText={setNomeConta}
            placeholder="Digite o nome da conta"
          />
          <Text style={styles.label}>Valor Total da Conta:</Text>
          <TextInput
            style={styles.input}
            value={valorTotal}
            onChangeText={setValorTotal}
            keyboardType="numeric"
            placeholder="Digite o valor total"
          />

          <Text style={styles.label}>Quantidade de Pessoas:</Text>
          <TextInput
            style={styles.input}
            value={pessoasTotais}
            onChangeText={setPessoasTotais}
            keyboardType="numeric"
            placeholder="Digite o número de pessoas"
          />

          <TouchableOpacity
            style={styles.button}
            onPress={gerarDistribuicaoInicial}
          >
            <Text style={styles.buttonText}>Gerar Distribuição</Text>
          </TouchableOpacity>
        </>
      ) : (
        <FlatList
          data={pessoas}
          keyExtractor={(item, index) => index.toString()}
          renderItem={({ item, index }) => (
            <View style={styles.pessoaContainer}>
              <TextInput
                style={styles.input}
                value={item.pessoa}
                onChangeText={(novoNome) => editarNomePessoa(index, novoNome)}
                placeholder="Nome da Pessoa"
              />
              <TextInput
                style={styles.input}
                value={item.valor.toString()}
                onChangeText={(novoValor) => editarValorPessoa(index, novoValor)}
                keyboardType="numeric"
                placeholder="Valor"
              />
            </View>
          )}
        />
      )}

      {geracaoConcluida && (
        <TouchableOpacity style={styles.button} onPress={salvarConta}>
          <Text style={styles.buttonText}>Salvar Conta</Text>
        </TouchableOpacity>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#f8f9fa",
    marginTop: 40,
  },
  label: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  input: {
    height: 50,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 15,
    paddingHorizontal: 10,
    backgroundColor: "#fff",
  },
  button: {
    backgroundColor: "#007bff",
    paddingVertical: 15,
    borderRadius: 8,
    alignItems: "center",
    marginBottom: 20,
  },
  buttonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "bold",
  },
  pessoaContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 15,
  },
});

export default AddAccountScreen;
