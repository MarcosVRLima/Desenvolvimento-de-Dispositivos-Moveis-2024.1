import React, { useEffect, useState, useContext } from "react";
import {
    View,
    Text,
    FlatList,
    TouchableOpacity,
} from "react-native";
import authContext from "@/context/authContext"; // Contexto de Autenticação
import { styles } from "./styles";
import { readDocumentByIdUser } from "@/firestore/readDocument";
import { useNavigation } from '@react-navigation/native';
import { router } from "expo-router";
import AuthContext from "@/context/authContext";

export default function Home() {
    const { user, logout } = useContext(AuthContext); // Obtém o usuário do contexto

    if (!user) {
        router.push("/login")
    }

    const navigation = useNavigation();
    const [contas, setContas] = useState([]);

    useEffect(() => {
        const fetchContas = async () => {
            setContas(await readDocumentByIdUser("contas", user.id));
        };

        fetchContas();

        // Configurando o botão "Adicionar Conta" no header
        navigation.setOptions({
            headerRight: () => (
                <TouchableOpacity
                    style={styles.button}
                    onPress={() => router.push('/addAccount')}
                >
                    <Text style={styles.buttonText}>+ Conta</Text>
                </TouchableOpacity>
            ),
        });
    }, [navigation, user]);

    const irParaDetalhesDaConta = (idConta) => {
        //navigation.navigate('DetalhesConta', { idConta });
    };

    return (
        <View style={styles.container}>
            <Text style={styles.greeting}>Olá, {user.nome}!</Text>
            <Text style={styles.label}>Suas contas cadastradas:</Text>

            <FlatList
                data={contas}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <TouchableOpacity
                        style={styles.contaContainer}
                        onPress={() => router.push(`/editAccount/${item.id}`)}
                        
                    >
                        <Text style={styles.contaText}>Conta: {item.nomeConta}</Text>
                        <Text style={styles.contaText}>
                            Valor Total: R${item.valortotal}
                        </Text>
                    </TouchableOpacity>
                )}
            />

            <View style={styles.footer}>
                <TouchableOpacity style={styles.button} onPress={() => router.push('/addAccount')}>
                    <Text style={styles.buttonText}>+ Conta</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button}>
                    <Text style={styles.buttonText}>Configurações</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.logoutButton} onPress={logout}>
                    <Text style={styles.logoutButtonText}>Logout</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}
