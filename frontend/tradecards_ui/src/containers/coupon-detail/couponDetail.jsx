import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getCouponDetail, getUserDetail, postReview } from './apiUtils';
import { convertBase64toImage } from '../../common-utils';
import './style.scss';
import NavBar from '../../components/nav-bar';
import { Avatar, Box, Button, InputLabel, MenuItem, Modal, Select, TextField, Typography } from '@mui/material';
import ReviewStars from '../../components/review-stars';
import ChatModal from './chat-modal';

const CouponDetail = () => {
  const { couponId, } = useParams();

  const [couponInfo, setCouponInfo,] = useState(null);
  const [user, setUser,] = useState({});
  const [isChatOpened, setIsChatOpened,] = useState(false);
  const [openReviewModa, setOpenReviewModal,] = useState(false);
  const [review, setReview,] = useState('');
  const [rating, setRating,] = useState(0);

  const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: '#fff',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
    color: 'hsl(318, 22%, 27%)',
  };

  useEffect(() => {
    getCouponDetail(couponId)
      .then((res) => {
        setCouponInfo(res);
        return getUserDetail(res?.userid);
      })
      .then((res) => setUser(res));
  }, []);

  const getAverageReview = (reviews) => {
    return reviews?.reduce((sum, review) => sum + review?.rating, 0) / reviews?.length;
  };

  const convertDate = (timestamp) => {
    const date = new Date(timestamp);

    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  };

  return (
    <div className='coupon-detail'>
      <NavBar />
      <div
        className='coupon-detail-image-container'
        style={
        { background: `linear-gradient(90deg, #1A1A1A 24.97%, #1A1A1A 38.3%, rgba(26, 26, 26, 0.0409746) 97.47%, #1A1A1A 100%), url(${convertBase64toImage(couponInfo?.couponImage)})`, }
      }>
        <img
          src={convertBase64toImage(couponInfo?.couponImage)}
          className='coupon-detail-image'
        />
        <div className='d-flex'>
          <div>
            <div className='coupon-detail-image-container-name'>
              {couponInfo?.couponName}
            </div>
            <div className='coupon-detail-image-container-brand'>
              {couponInfo?.couponBrand}
            </div>
          </div>
          <div>
            <div className='coupon-detail-image-container-price'>
              <span style={{ fontWeight: 'bold', fontSize: '24px', }}>Original Price- </span>
              $ {couponInfo?.couponValue}
            </div>
            <div className='coupon-detail-image-container-price'>
              <span style={{ fontWeight: 'bold', fontSize: '24px', }}>Selling Price- </span>
              $ {couponInfo?.couponSellingPrice}
            </div>
          </div>
          <div>
            <div style={{ marginBottom: '16px', fontSize: '20px', fontWeight: 'bold', }}>Willing to buy?</div>
            <Button
              variant='contained'
              className='coupon-detail-image-container-btn'
              onClick={() => setIsChatOpened(true)}
            >
              Have a chat with seller
            </Button>
          </div>
        </div>
      </div>
      <div className='coupon-detail-info'>
        <div className='coupon-detail-info-desc-heading'>
          About the Coupon
        </div>
        <div className='coupon-detail-info-desc'>
          {couponInfo?.couponDesc}
        </div>
        <div className='coupon-detail-info-location'>
          <div>{couponInfo?.couponLocation}</div>
        </div>
        <div className='coupon-detail-info-seller'>
          <div style={{ fontWeight: 'bold', marginBottom: '12px', }}>Seller Information</div>
          <div className='coupon-detail-info-seller-name'>
            <span>{`${user?.firstName} ${user?.lastName || ''}`}</span>
            <span className='coupon-detail-info-seller-name-star'>
              {user?.receivedReviews?.length ? <ReviewStars review={getAverageReview(user?.receivedReviews)}/> : null}
            </span>
            <Button
              onClick={() => setOpenReviewModal(true)}
              style={{ backgroundColor: 'hsl(318, 22%, 27%)', }}
              variant='contained'
            >Review Seller</Button>
          </div>
          <div
            style={{ fontWeight: 'bold', }}
            className='coupon-detail-info-seller-popreviews'
          >Popular Seller Reviews</div>
          <div className='coupon-detail-info-seller-reviews'>
            { !!user?.receivedReviews?.length && user?.receivedReviews?.map((review) => (
              <div
                key={review?.reviewId}
                className='coupon-detail-info-seller-reviews-wrapper'
              >
                <Avatar />
                <div
                  className='coupon-detail-info-seller-reviews-wrapper-review'
                >
                  <div className='review-content'>{review?.content}</div>
                  <ReviewStars review={review?.rating}/>
                  <div className='review-date'>{convertDate(review?.reviewDate)}</div>
              </div>
              </div>
            ))}
          </div>
        </div>
      </div>
      {
        isChatOpened
          ? <ChatModal
              isOpen={isChatOpened}
              onClose={() => setIsChatOpened(false)}
              receiver={user}
        />
          : null
      }
      {
        openReviewModa && (
          <Modal open={openReviewModa}
            onClose={() => setOpenReviewModal(false)}>
            <Box sx={modalStyle}>
              <Typography
                variant="h6"
                component="h2"
              >Your Review Matters</Typography>
              <Typography style={{ display: 'flex', flexDirection: 'column', gap: '20px', alignItems: 'center', }}>
                <TextField
                  style={{ width: '100%', }}
                  type='text'
                  onChange={(e) => setReview(e.target.value)}
                  label= 'Enter your honest review'/>
                <div style={{ display: 'flex', alignItems: 'center', flexDirection: 'column', gap: '5px', }}>
                  <InputLabel
                    id="demo-simple-select-label"
                    style={{ marginTop: '10px', }}>Rating</InputLabel>
                  <Select
                    label="Rating"
                    style={{ width: '100px', }}
                    onChange={(e) => setRating(Number(e.target.value))}
                  >
                    <MenuItem value={0.5}>0.5</MenuItem>
                    <MenuItem value={1}>1</MenuItem>
                    <MenuItem value={1.5}>1.5</MenuItem>
                    <MenuItem value={2}>2</MenuItem>
                    <MenuItem value={2.5}>2.5</MenuItem>
                    <MenuItem value={3}>3</MenuItem>
                    <MenuItem value={3.5}>3.5</MenuItem>
                    <MenuItem value={4}>4</MenuItem>
                    <MenuItem value={4.5}>4.5</MenuItem>
                    <MenuItem value={5}>5</MenuItem>
                  </Select>
                </div>
                <div>
                  <Button
                    variant='primary'
                    style={{ backgroundColor: 'hsl(318, 22%, 27%)', color: '#fff', }}
                    onClick={() => {
                      if (rating > 0) {
                        postReview({ review, rating, reviewedUserID: user?.userid, })
                          .then((res) => window.location.reload());
                      }
                    }}
                  >Submit</Button></div>
              </Typography>
            </Box>
          </Modal>
        )
      }
    </div>
  );
};

export default CouponDetail;
