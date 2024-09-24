import React, { useState } from "react";
import { Text, View, Image, TextInput, TouchableOpacity, Alert, ActivityIndicator } from 'react-native';
import { style } from "./styles"; // Certifique-se de que o estilo está acessível
import logo from './../../assets/calcular.png';
import { MaterialIcons } from '@expo/vector-icons';
import { createDocument } from "@/firestore/createDocument"; // Importe a função para criar usuário
import { router } from "expo-router";

export default function CreateUser() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [hidePassword, setHidePassword] = useState(true);

    async function handleCreateUser() {
        setLoading(true);

        try {
            if (!email || !password) {
                return Alert.alert('Atenção', 'Informe os campos obrigatórios!');
            }

            // Verifica se o formato do e-mail é válido
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                return Alert.alert('Erro', 'Formato de e-mail inválido!');
            }

            // Cria o usuário no Firestore
            await createDocument('usuario', { email, nome: name ,senha: password }); // Adicione a função que salva no Firestore
            Alert.alert('Sucesso', 'Usuário criado com sucesso!');
            router.push("/login")
        } catch (error) {
            console.error("Erro ao criar usuário:", error);
            Alert.alert('Erro', 'Ocorreu um erro ao tentar criar o usuário.');
        } finally {
            setLoading(false);
        }
    }

    return (
        <View style={style.container}>
            <View style={style.boxTop}>
                <Image source={logo} style={style.logo} resizeMode="contain" />
                <Text style={style.title}>Criar nova conta</Text>
            </View>
            <View style={style.boxMid}>
                <Text style={style.labelInput}>Nome:</Text>
                <View style={style.boxInput}> 
                    <TextInput 
                        style={style.fieldInput} 
                        value={name} 
                        onChangeText={setName} 
                    />        
                    <MaterialIcons name="person" size={20} color={'gray'} />
                </View>

                <Text style={style.labelInput}>Endereço de e-mail:</Text>
                <View style={style.boxInput}>
                    <TextInput 
                        style={style.fieldInput} 
                        value={email} 
                        onChangeText={setEmail} 
                        keyboardType="email-address"
                        autoCapitalize="none"
                    />
                    <MaterialIcons name="email" size={20} color={'gray'} />
                </View>

                <Text style={style.labelInput}>Senha:</Text>
                <View style={style.boxInput}>
                    <TextInput 
                        style={style.fieldInput} 
                        value={password} 
                        onChangeText={setPassword} 
                        secureTextEntry={hidePassword} // Alterna entre mostrar e esconder a senha
                        autoCapitalize="none" // Desativa auto capitalização para senha 
                    />
                    <TouchableOpacity onPress={() => setHidePassword(!hidePassword)}>
                        <MaterialIcons 
                            name={hidePassword ? "visibility-off" : "visibility"} 
                            size={20} 
                            color={'gray'} 
                        />
                    </TouchableOpacity>
                </View>
            </View>
            <View style={style.boxBottom}>
                <TouchableOpacity style={style.buttonLogin} onPress={handleCreateUser}>
                    {
                        loading ? 
                            <ActivityIndicator color={'#FFFF'} /> : <Text style={style.buttonText}>Criar Conta</Text>
                    }
                </TouchableOpacity>
            </View>
        </View>
    );
}
