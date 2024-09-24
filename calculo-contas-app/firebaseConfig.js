// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore"; // Adicione o Firestore

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyDU0XarzH-EntB5Fx_03qGtVXa8tuceQwk",
  authDomain: "calculo-contas-app.firebaseapp.com",
  projectId: "calculo-contas-app",
  storageBucket: "calculo-contas-app.appspot.com",
  messagingSenderId: "1065574012002",
  appId: "1:1065574012002:web:62a275b1ac582ff77c6d3b"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Firestore and export it
export const db = getFirestore(app);

export default app;
