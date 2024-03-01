import { onAuthStateChanged } from 'firebase/auth';
import React, { createContext, useEffect, useState } from 'react';
import { auth } from '../firebase';
import PropTypes from 'prop-types';

const AuthorizationContext = createContext();

const AuthorizationContextProvider = ({ children, }) => {
  const [user, setUser,] = useState({});

  useEffect(() => {
    const reset = onAuthStateChanged(auth, (user) => {
      setUser(user);
    });
    return () => {
      reset();
    };
  }, []);
  return (
    <AuthorizationContext.Provider value={{ user, }}>
      {children}
    </AuthorizationContext.Provider>
  );
};
AuthorizationContextProvider.propTypes = { children: PropTypes.element, };

export {
  AuthorizationContext,
  AuthorizationContextProvider,
};
