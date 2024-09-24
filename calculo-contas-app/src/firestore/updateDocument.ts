import { doc, updateDoc } from "firebase/firestore";
import { db } from "../../firebaseConfig";

export async function updateDocument(collectionName, docId, data) {
    try {
        const docRef = doc(db, collectionName, docId);
        await updateDoc(docRef, data);
        console.log("Documento atualizado com sucesso!");
    } catch (error) {
        console.error("Erro ao atualizar documento: ", error);
        throw error;
    }
}
