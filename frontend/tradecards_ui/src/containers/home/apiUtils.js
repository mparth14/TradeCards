import { getStorage } from '../../common-utils';

const user = JSON.parse(getStorage('userInfo'));
const location = getStorage('location');

// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';
const getAllCoupons = () => {
  return fetch(REACT_APP_END_POINT_PROD + '/api/coupons', {
    method: 'GET',
    mode: 'cors',
    headers: { Authorization: `Bearer ${user.token}`, },
  })
    .then((res) => res.json())
    .then((res) => res.filter((resItem) => resItem?.userid !== user?.userId))
    .then((res) => res.filter((resItem) => resItem?.couponLocation === location || location === 'All location' || !location))
  ;
};

const getCouponsByCategory = ({ categoryId, }) => {
  return fetch(REACT_APP_END_POINT_PROD + '/api/coupons', {
    method: 'GET',
    mode: 'cors',
    headers: { Authorization: `Bearer ${user.token}`, },
  })
    .then((res) => res.json())
    .then((res) => {
      if (categoryId) {
        console.log(res.filter((resItem) => resItem.categoryID === Number(categoryId))
          .filter((resItem1) => resItem1?.userid !== user?.userId)
          .filter((resItem1) => resItem1?.couponLocation === location || location === 'All location' || !location));
        return res.filter((resItem) => resItem.categoryID === Number(categoryId))
          .filter((resItem1) => resItem1?.userid !== user?.userId)
          .filter((resItem1) => resItem1?.couponLocation === location || location === 'All location' || !location);
      } else {
        return res;
      }
    });
};

const getCouponsByUser = ({ userid, }) => {
  return fetch(REACT_APP_END_POINT_PROD + '/api/coupons', {
    method: 'GET',
    mode: 'cors',
    headers: { Authorization: `Bearer ${user.token}`, },
  })
    .then((res) => res.json())
    .then((res) => {
      if (userid) {
        return res.filter((resItem) => resItem.userid === Number(userid));
      } else {
        return res;
      }
    });
};

export {
  getAllCoupons,
  getCouponsByCategory,
  getCouponsByUser,
};
