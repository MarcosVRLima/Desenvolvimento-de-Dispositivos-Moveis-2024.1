import { AuthProvider } from "@/context/authContext";
import { Stack } from "expo-router";

export default function Layout(){
    return(
        <AuthProvider>
            <Stack screenOptions={{headerShown: false}}>
                <Stack.Screen name="login/index"/>
                <Stack.Screen name="signup/index"/>
                <Stack.Screen name="home/index"/>
                <Stack.Screen name="editAccount/[id]"/>
            </Stack> 
        </AuthProvider>
    )
}