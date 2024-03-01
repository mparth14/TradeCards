import { doc, setDoc } from 'firebase/firestore';
import { setStorage } from '../../common-utils';
import { auth, db } from '../../firebase';
import { createUserWithEmailAndPassword } from 'firebase/auth';

// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const onLogin = (userName, password) => {
  const data = {
    emailId: userName,
    password,
  };
  return fetch(REACT_APP_END_POINT_PROD + '/api/login', {
    method: 'POST',
    mode: 'cors',
    headers: { 'Content-Type': 'application/json', },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((res) => {
      setStorage('userInfo', JSON.stringify(res));
      return res;
    })
  ;
};

const onSignup = (userName, password, firstName, lastName) => {
  const data = {
    emailID: userName,
    password,
    firstName,
    lastName,
  };
  return fetch(REACT_APP_END_POINT_PROD + '/api/signup', {
    method: 'POST',
    mode: 'cors',
    headers: { 'Content-Type': 'application/json', },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((res) => {
      try {
        createUserWithEmailAndPassword(auth, res.emailID, password)
          .then(async (resItem) => {
            try {
              await setDoc(doc(db, 'users', resItem.user.uid), {
                uid: resItem.user.uid,
                displayName: firstName,
                email: res.emailID,
              });
              await setDoc(doc(db, 'userChats', resItem.user.uid), {});
            } catch (err) {
              console.log(err);
            }
          })
          .catch((error) => {
            console.log(error);
          });
      } catch (err) {
        console.log(err);
      }

      return res;
    })
  ;
};

export {
  onLogin,
  onSignup,
};
