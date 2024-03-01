import { getStorage } from '../../common-utils';

const user = JSON.parse(getStorage('userInfo'));
// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const getCategories = () => {
  return fetch(REACT_APP_END_POINT_PROD + '/api/categories', {
    mode: 'cors',
    headers: { Authorization: `Bearer ${user.token}`, },
  })
    .then((res) => res.json())
    .then((res) => res.filter((resItem) => resItem?.categoryName));
};

export { getCategories, };
