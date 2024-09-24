import React, { useContext, useEffect, useState } from "react";
import {
  View,
  Text,
} from "react-native";
import { router, useLocalSearchParams } from "expo-router";
import AuthContext from "@/context/authContext";
import { readDocument } from "@/firestore/readDocument";

export default function EditAccount() {
  

  const { user, logout } = useContext(AuthContext); // Obtém o usuário do contexto

  if (!user) {
    router.push("/login");
  }

  const { id } = useLocalSearchParams();
  const [conta, setConta] = useState(null); // Inicializa como null para melhor controle

  useEffect(() => {
    const fetchContas = async () => {
      try {
        const dados = await readDocument("contas", id);
        setConta(dados);
      } catch (error) {
        console.error("Erro ao buscar conta: ", error);
      }
    };

    fetchContas();
  }, [id]);

  console.log(conta)
  
  return (
    <View>
      {conta ? (
        <Text>Nome da Conta: {conta.nomeConta}</Text> // Ajuste o campo conforme a estrutura do seu documento
      ) : (
        <Text>Carregando...</Text>
      )}
    </View>
  );
};