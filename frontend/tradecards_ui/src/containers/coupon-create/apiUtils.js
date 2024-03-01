/* eslint-disable */
const user = JSON.parse(localStorage.getItem('userInfo'));

// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const onCouponCreate = (couponTitle, couponDescription, couponVendor, couponValidity,
  couponValue, couponPrice, sold, couponType, couponCategory, couponListingDate,
  couponLocation, userId, categoryId, couponImage) => {
  const data = {
    couponName: couponTitle,
    couponDesc: couponDescription,
    couponBrand: couponVendor,
    expiryDate: couponValidity,
    couponValue: couponValue,
    couponSellingPrice: couponPrice,
    sold: sold,
    online: couponType,
    couponCategory: couponCategory,
    couponListingDate: couponListingDate,
    couponLocation: couponLocation,
    userid: userId,
    categoryID: categoryId,
    couponImage: couponImage,
  };
  return fetch(REACT_APP_END_POINT_PROD+ '/api/coupon/create-coupons', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json',
      'Authorization': `Bearer ${user.token}` },
    body: JSON.stringify(data),
  }).then((res) => res);
};

const onCouponEdit = (couponTitle, couponDescription, couponVendor, couponValidity,
  couponValue, couponPrice, sold, couponType, couponCategory, couponListingDate,
  couponLocation, userId, categoryId, couponImage, id) => {
  const data = {
    userid: userId,
    couponListingDate: couponListingDate,
    couponSellingPrice: couponPrice,
    couponDesc: couponDescription,
    couponValue: couponValue,
    couponName: couponTitle,
    couponBrand: couponVendor,
    expiryDate: couponValidity,
    couponLocation: couponLocation,
    isOnline: couponType,
    categoryID: categoryId,
    isSold: sold,
    couponCategory: couponCategory,
    couponImage: couponImage,
  };
  return fetch(REACT_APP_END_POINT_PROD + '/api/coupon/update-coupon/' + id, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json',
      'Authorization': `Bearer ${user.token}` },
    body: JSON.stringify(data),
  }).then((res) => res);
};

export { onCouponCreate, onCouponEdit };
