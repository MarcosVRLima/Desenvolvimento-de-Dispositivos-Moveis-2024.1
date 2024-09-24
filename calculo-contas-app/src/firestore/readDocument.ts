import {
    doc,
    getDoc,
    collection,
    query,
    where,
    getDocs,
} from "firebase/firestore";
import { db } from "../../firebaseConfig";

// Função para ler documento por ID
export async function readDocument(collectionName, docId) {
    try {
        const docRef = doc(db, collectionName, docId);
        const docSnap = await getDoc(docRef);

        if (docSnap.exists()) {
            console.log("Documento encontrado:", docSnap.data());
            return docSnap.data();
        } else {
            console.log("Nenhum documento encontrado!");
            return null;
        }
    } catch (error) {
        console.error("Erro ao ler documento: ", error);
        throw error;
    }
}

// Função para ler documento por email
export async function readDocumentByEmail(collectionName, email) {
    try {
        const q = query(
            collection(db, collectionName),
            where("email", "==", email)
        );
        const querySnapshot = await getDocs(q);

        let userData = null;

        querySnapshot.forEach((doc) => {
            userData = { id: doc.id, ...doc.data() }; // Pega o documento correspondente
        });

        return userData; // Retorna os dados do usuário se encontrado, ou null se não encontrado
    } catch (error) {
        console.error("Erro ao buscar documento: ", error);
        throw error;
    }
}

// Função para ler documento por idusuario
export async function readDocumentByIdUser(collectionName, idUser) {
    try {
        const q = query(collection(db, "contas"), where("idusuario", "==", idUser));
        const querySnapshot = await getDocs(q);
        const contasList = [];
        querySnapshot.forEach((doc) => {
            contasList.push({ id: doc.id, ...doc.data() });
        });

        return contasList; // Retorna os dados do usuário se encontrado, ou null se não encontrado
    } catch (error) {
        console.error("Erro ao buscar documento: ", error);
        throw error;
    }
}
