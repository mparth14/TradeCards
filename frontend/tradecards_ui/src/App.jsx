import React from 'react';
import './App.css';
import { BrowserRouter as Router } from 'react-router-dom';
import MainRoute from './routes';
import './containers/index.scss';
import './components/index.scss';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App () {
  return (
    <Router>
      <MainRoute />
      <ToastContainer
        position="bottom-left"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
    </Router>
  );
}

export default App;
