import React from 'react';
import Login from './containers/login';
import ForgetPassword from './containers/forgetPassword';
import Home from './containers/home';
import CouponGrid from './containers/coupon-grid/';
import CouponCreate from './containers/coupon-create/couponCreate';
import CouponDetail from './containers/coupon-detail';
import UserProfile from './containers/user-profile';
import CouponGridByUser from './containers/coupon-grid-by-user/couponGridByUser';
import MyMessages from './containers/my-messages';

const routes = [
  {
    id: 'login',
    route: 'login',
    component: <Login isLogin={true} />,
  },
  {
    id: 'signup',
    route: 'signup',
    component: <Login />,
  },
  {
    id: 'forget-password',
    route: 'forget-password',
    component: <ForgetPassword />,
  },
  {
    id: 'home',
    route: 'home',
    component: <Home />,
  },
  {
    id: 'coupon-listing',
    route: 'coupon-listing',
    component: <CouponGrid />,
  },
  {
    id: 'coupon-listing-by-user',
    route: 'coupon-listing-by-user',
    component: <CouponGridByUser />,
  },
  {
    id: 'coupon-create',
    route: 'coupon-create',
    component: <CouponCreate />,
  },
  {
    id: 'coupon-edit',
    route: 'coupon-edit/:id',
    component: <CouponCreate isEdit={true} />,
  },
  {
    id: 'coupon-details',
    route: 'coupon-detail/:couponId',
    component: <CouponDetail />,
  },
  {
    id: 'user-profile',
    route: 'user-profile',
    component: <UserProfile />,
  },
  {
    id: 'my-messages',
    route: 'messages',
    component: <MyMessages />,
  },
];

export default routes;
