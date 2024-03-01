import { getStorage } from '../../common-utils';

const user = JSON.parse(getStorage('userInfo'));
// const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

const getCouponDetail = (id) => {
  return fetch(REACT_APP_END_POINT_PROD + '/api/coupon/get-coupon/' + id, {
    mode: 'cors',
    headers: { Authorization: `Bearer ${user.token}`, },
  })
    .then((res) => res.json());
};

const getUserDetail = (id) => {
  return fetch(REACT_APP_END_POINT_PROD + `/api/users/details/${id}`,
    {
      mode: 'cors',
      headers: { Authorization: `Bearer ${user.token}`, },
    })
    .then((res) => res.json());
};

const postReview = (params) => {
  const {
    review,
    rating,
    reviewedUserID,
  } = params;

  return fetch(REACT_APP_END_POINT_PROD + '/api/reviews/create', {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${user.token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      content: review,
      rating,
      reviewedUserID,
      reviewerUserID: user?.userId,
    }),
  });
};

export { getCouponDetail, getUserDetail, postReview, };
