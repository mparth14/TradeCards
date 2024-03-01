import React, { useState, useEffect } from 'react';
import './style.scss';
import NavBar from '../../components/nav-bar';
import { getStorage } from '../../common-utils';
import TabItem from './component/tab';
import CouponListingByUser from '../../components/coupon-listing-by-user/couponListingByUser';
import { getCouponsByUser } from '../home/apiUtils';
import { Box, Modal, TextField, Typography } from '@mui/material';
import { editUser } from './apiUtils';

const UserProfile = () => {
  const user = JSON.parse(getStorage('userInfo'));
  const userid = user.userId;
  const [couponsData, setCouponsData,] = useState([]);
  const [isEditField, setIsEditField,] = useState('');
  const [email, setEmail,] = useState('');
  const [password, setPassword,] = useState('');
  const [isPassPrompt, setIsPassPrompt,] = useState(false);
  const [firstName, setFirstName,] = useState('');
  const [lastName, setLastName,] = useState('');

  useEffect(() => {
    getCouponsByUser({ userid, })
      .then((res) => setCouponsData(res));
    console.log(couponsData);
    console.log(userid);
  }, []);

  const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    boxShadow: 24,
    bgcolor: '#fff',
    color: 'hsl(318, 22%, 27%)',
    p: 4,
    height: '150px',
    outline: 'none',
    border: 'none',
  };

  const promptPassword = () => {
    editUser({ email, password, firstName, lastName, });
    setIsPassPrompt(false);
    setIsEditField('');
  };

  useEffect(() => {
    setEmail(user?.email);
    setFirstName(user?.firstName);
    setLastName(user?.lastName);
  }, []);

  return (
    <div className='user-profile'>
      <NavBar />
      <div className='user-profile-detail'>
        <span className='user-profile-detail-name'>Hi, {user?.firstName}{user?.lastName ? `-${user?.lastName}` : ''}</span>
      </div>
      <div className='user-profile-account'>
        <div className='user-profile-account-head'>Account Details</div>
        <div className='user-profile-account-subhead'>
          <div className='user-profile-account-subhead-container'>
            <span className='user-profile-account-subhead-title'>Email address</span>
            <span className='user-profile-account-subhead-value'>{user?.email}</span>
          </div>
        </div>
        <div className='user-profile-account-subhead'>
          <div className='user-profile-account-subhead-container'>
          <span className='user-profile-account-subhead-title'>First Name</span>
          {isEditField !== 'firstname'
            ? <span className='user-profile-account-subhead-value'>{firstName}</span>
            : <TextField
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                onKeyDown={(e) => { if (e.key === 'Enter') setIsPassPrompt(true); }}
                />}
          </div>
          <i
            className="fa-solid fa-pen-to-square"
            style={{ color: 'hsl(318, 22%, 27%)', cursor: 'pointer', }}
            onClick={() => setIsEditField('firstname')}
          ></i>
        </div>
        <div className='user-profile-account-subhead'>
          <div className='user-profile-account-subhead-container'>
          <span className='user-profile-account-subhead-title'>Last Name</span>
          {isEditField !== 'lastname'
            ? <span className='user-profile-account-subhead-value'>{lastName}</span>
            : <TextField
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                onKeyDown={(e) => { if (e.key === 'Enter') setIsPassPrompt(true); }}
                />}
          </div>
          <i
            className="fa-solid fa-pen-to-square"
            style={{ color: 'hsl(318, 22%, 27%)', cursor: 'pointer', }}
            onClick={() => setIsEditField('lastname')}
          ></i>
        </div>
        <div className='user-profile-account-subhead'>
          <div className='user-profile-account-subhead-container'>
            <span className='user-profile-account-subhead-title'>Password</span>
            {isEditField !== 'pass'
              ? <span className='user-profile-account-subhead-value'>*******</span>
              : <TextField
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  onKeyDown={(e) => { if (e.key === 'Enter') setIsPassPrompt(true); }}
                />}
          </div>
          <i
            className="fa-solid fa-pen-to-square"
            style={{ color: 'hsl(318, 22%, 27%)', cursor: 'pointer', }}
            onClick={() => setIsEditField('pass')}
          ></i>
        </div>
      </div>
      <div className='user-profile-coupons'>
        <TabItem />
      </div>
      <div className='coupon-listing'
        style={{ paddingBottom: '40px', }}>
        <CouponListingByUser
          couponLists={couponsData}
        />
      </div>
      <Modal
        open={isPassPrompt}
      >
        <Box
          sx={modalStyle}
        >
          <Typography
            variant="h6"
            component="h2"
          >
            Enter the password
          </Typography>
          <TextField
            type='password'
            required
            label="Password"
            onChange={(e) => setPassword(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') promptPassword();
            }}
            style={{ marginTop: '20px', }}
          />
        </Box>
      </Modal>
    </div>
  );
};

export default UserProfile;
