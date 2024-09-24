import { doc, deleteDoc } from "firebase/firestore";
import { db } from "../../firebaseConfig";

export async function deleteDocument(collectionName, docId) {
    try {
        const docRef = doc(db, collectionName, docId);
        await deleteDoc(docRef);
        console.log("Documento deletado com sucesso!");
    } catch (error) {
        console.error("Erro ao deletar documento: ", error);
        throw error;
    }
}
