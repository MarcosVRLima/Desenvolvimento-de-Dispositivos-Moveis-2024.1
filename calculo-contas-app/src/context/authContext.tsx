import React, { createContext, useState, ReactNode } from 'react';
import { Alert } from 'react-native';
import { readDocumentByEmail } from "@/firestore/readDocument";
import { router } from "expo-router";

// Define a interface para o contexto de autenticação
interface AuthContextType {
    user: any; // Você pode definir um tipo mais específico para o usuário se souber a estrutura exata
    login: (email: string, password: string) => Promise<boolean>; // Função de login que retorna uma promessa
    logout: () => void; // Função de logout
}

// Cria o contexto com o tipo definido
const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<any>(null); // Estado para armazenar o usuário logado

    // Função para realizar o login
    const login = async (email: string, password: string): Promise<boolean> => {
        try {
            if (!email || !password) {
                Alert.alert('Atenção', 'Informe o e-mail e a senha!');
                return false;
            }

            const userData = await readDocumentByEmail('usuario', email);

            if (userData && userData.senha === password) {
                setUser(userData);
                Alert.alert('Sucesso', 'Login realizado!');
                return true;
            } else {
                Alert.alert('Erro', 'Email ou senha incorreta!');
                return false;
            }
        } catch (error) {
            console.error("Erro ao realizar login:", error);
            Alert.alert('Erro', 'Ocorreu um erro ao tentar realizar o login.');
            return false;
        }
    };

    // Função para realizar o logout
    const logout = () => {
        setUser(null);
        router.replace('/login');
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;
