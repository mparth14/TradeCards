// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: 'AIzaSyCAbpamve5AIdlJDwGYRIct1--nVwaU0kA',
  authDomain: 'tradecards-71ce0.firebaseapp.com',
  projectId: 'tradecards-71ce0',
  storageBucket: 'tradecards-71ce0.appspot.com',
  messagingSenderId: '1000479317919',
  appId: '1:1000479317919:web:d08e590005181d8471a7aa',
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth();
const db = getFirestore();

export {
  app,
  auth,
  db,
};
