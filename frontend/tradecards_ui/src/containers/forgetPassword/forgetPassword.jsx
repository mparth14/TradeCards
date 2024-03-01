/* eslint-disable import/no-extraneous-dependencies */
import { Button } from '@mui/material';
import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { onForgotPassword, onSendOtp, onSetNewPassword } from './apiUtils';
import { useNavigate } from 'react-router-dom';
import InputHolder from '../login/components/input';

function ForgetPassword (props) {
  const { isForgetPassword, } = props;

  const [email, setEmail,] = useState('');
  const [confirmation, setConfirmation,] = useState('');
  const navigate = useNavigate();
  const [otp, setOtp,] = useState('');
  const [newPass, setNewPass,] = useState('');

  const onSubmit = () => {
    if (confirmation === 'Password reset link sent!') {
      onSendOtp({ email, otp, }).then((res) => {
        setConfirmation('Otp setup successful');
      });
    } else if (confirmation === 'Otp setup successful') {
      onSetNewPassword({ email, newPass, }).then(res => {
        setConfirmation('New password setup succesfully');
        navigate('/login');
      });
    } else {
      onForgotPassword(email).then(res => {
        setConfirmation('Password reset link sent!');
      });
    }
  };

  const onNavigate = () => {
    if (isForgetPassword) {
      navigate('/forget-password');
    } else {
      navigate('/login');
    }
  };

  const getValue = () => {
    if (confirmation === 'Password reset link sent!') {
      return otp;
    } else if (confirmation === 'Otp setup successful') {
      return newPass;
    } else {
      return email;
    }
  };

  const getOnchange = (val) => {
    if (confirmation === 'Password reset link sent!') {
      setOtp(val);
    } else if (confirmation === 'Otp setup successful') {
      setNewPass(val);
    } else {
      setEmail(val);
    }
  };

  const getPlaceHolder = () => {
    if (confirmation === 'Password reset link sent!') {
      return 'Your OTP please';
    } else if (confirmation === 'Otp setup successful') {
      return 'Your new Password';
    } else {
      return 'Your registered email address';
    }
  };

  return (
    <div className='login-wrapper'>
     <div className='login'>
        <div className='login-image'>
          <img
            src='/img/logo.png'
          />
        </div>
        { confirmation ? <div className='login-heading'>Enter your OTP</div> : <div className='login-heading'>Enter your email below:</div> }
        <InputHolder
          value={getValue()}
          onChange={getOnchange}
          placeholder={getPlaceHolder()}
          type={confirmation === 'Otp setup successful' ? 'password' : 'text'}
        />
        <div className='login-switch'>
          {confirmation}
        </div>
        <Button
          variant="contained"
          onClick={onSubmit}
          className='login-submit'
        >
          Submit
        </Button>
        <div
          onClick={onNavigate}
          className='login-switch'
        >
          {'Go back'}
        </div>
     </div>
    </div>
  );
}

ForgetPassword.propTypes = { isForgetPassword: PropTypes.bool, };

ForgetPassword.defaultProps = { isForgetPassword: false, };

export default ForgetPassword;
