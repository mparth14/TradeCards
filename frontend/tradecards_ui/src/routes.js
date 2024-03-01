import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import routes from './routesInfo';

function MainRoute () {
  return (
    <Routes>
      {
        routes?.map((route) => (
        <Route
          key={route?.id}
          path={route?.route}
          element={route?.component}
        />))
      }
      <Route
        path="*"
        element={<Navigate to="/login" />}
      />
    </Routes>
  );
}

export default MainRoute;
