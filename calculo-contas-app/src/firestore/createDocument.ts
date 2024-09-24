import { collection, addDoc } from "firebase/firestore";
import { db } from "../../firebaseConfig"; // Certifique-se de ajustar o caminho

export async function createDocument(collectionName, data) {
    try {
        const docRef = await addDoc(collection(db, collectionName), data);
        console.log("Documento adicionado com ID: ", docRef.id);
        return docRef.id;
    } catch (error) {
        console.error("Erro ao adicionar documento: ", error);
        throw error;
    }
}
