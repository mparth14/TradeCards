import { getStorage, setStorage } from '../../common-utils';

const user = JSON.parse(getStorage('userInfo'));
// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const editUser = (params) => {
  const {
    email,
    password,
    firstName,
    lastName,
  } = params;

  const body = {
    emailID: email,
    ...(password && { password, }),
    firstName,
    lastName,
  };
  return fetch(REACT_APP_END_POINT_PROD + '/api/users/update-user', {
    method: 'PUT',
    body: JSON.stringify(body),
    headers: { Authorization: `Bearer ${user.token}`, 'Content-Type': 'application/json', },
  })
    .then((res) => res.json())
    .then((res) => {
      setStorage('userInfo', JSON.stringify({ ...JSON.parse(getStorage('userInfo')), email, firstName, lastName, }));
      return res;
    });
};

export { editUser, };
