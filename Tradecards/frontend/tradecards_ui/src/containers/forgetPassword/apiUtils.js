// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const onForgotPassword = (email) => {
  const data = { emailID: email, };
  return fetch(REACT_APP_END_POINT_PROD + '/api/forget-password-request', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((res) => console.log(res))
  ;
};

const onSendOtp = ({ otp, email, }) => {
  const data = {
    emailID: email,
    otp,
  };

  return fetch(REACT_APP_END_POINT_PROD + '/api/verify-otp', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((res) => res);
};

const onSetNewPassword = ({ email, newPass, }) => {
  const data = {
    emailID: email,
    newPassword: newPass,
  };

  return fetch(REACT_APP_END_POINT_PROD + '/api/set-new-password', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((res) => res);
};

export { onForgotPassword, onSendOtp, onSetNewPassword, };
