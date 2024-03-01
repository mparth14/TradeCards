/* eslint-disable */
import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import { Box, Button, Card, CardActions, CardContent, CardMedia, Typography } from '@mui/material';
import PropTypes from 'prop-types';
import { coupons } from '../coupon-listing/constants';
import { useNavigate } from 'react-router-dom';
import { convertBase64toImage } from '../../common-utils';
import { toast } from 'react-toastify';
import './modal.css';

const CouponListingByUser = (props) => {
  const {
    couponLists,
  } = props;

  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [selectedCoupon, setSelectedCoupon] = useState(null);
  const [coupons, setCoupons] = useState(couponLists);

  // const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
  const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

  const onEdit = (event) => {
    event.stopPropagation();
    const couponID = event.currentTarget.getAttribute('data-couponid');
    navigate(`/coupon-edit/${couponID}`);
  };

  const onDelete = (event) => {
    event.stopPropagation();
    const couponID = event.currentTarget.getAttribute('data-couponid');
    setSelectedCoupon(couponID);
    setShowModal(true);
  };

  const confirmDelete = () => {
    const couponID = selectedCoupon;
    const user = JSON.parse(localStorage.getItem('userInfo'));
    const fetchData = async () => {
      try {
        const response = await fetch(REACT_APP_END_POINT_PROD + '/api/coupon/delete-coupon/' + couponID, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${user.token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        } else if (response.ok) {
          toast.success('Coupon deleted successfully!');
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();

    const updatedCoupons = coupons.filter(coupon => coupon.couponID !== couponID);
    setCoupons(updatedCoupons);
    window.location.reload();
    setShowModal(false);
    setSelectedCoupon(null);
  };

  return (
    <>
      <Box sx={{ flexGrow: 1, }}>
      {couponLists && couponLists.length > 0 ? (
        <Grid
          container
          spacing={{ xs: 2, md: 3, }}
          columns={{ xs: 4, sm: 8, md: 12, }}
        >
          {
            couponLists?.map((coupon) => (
              <Grid
                item
                key={coupon?.couponID || coupon?.couponName}
                onClick={() => {
                  navigate(`/coupon-detail/${coupon?.couponID}`);
                }}
              >
                <Card sx={{ maxWidth: 300, minHeight: 300, }}>
                  <CardMedia
                    sx={{ height: 140, }}
                    image={convertBase64toImage(coupon?.couponImage)}
                    title={coupon?.couponName}
                  />
                  <CardContent className='coupon-listing-card-content'>
                    <Typography
                      gutterBottom
                      variant='h5'
                      component="div"
                      className='coupon-listing-card-content-heading'
                    >
                      {coupon?.couponName}
                    </Typography>
                    <Typography
                      variant='body2'
                      color="text.secondary"
                      className='coupon-listing-card-content-desc'
                    >
                      {coupon?.couponDesc}
                    </Typography>
                    {
                      coupon?.couponImage
                        ? (
                          <Typography
                            variant='body2'
                            color="text.secondary"
                            className='coupon-listing-card-content-desc'
                          >
                            <i className="fa-solid fa-location-dot"></i>
                            {coupon?.couponLocation}
                          </Typography>
                          )
                        : null
                    }
                  </CardContent>
                  <CardActions sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Button size="small"
                      onClick={onEdit}
                      data-couponid={coupon?.couponID}>
                      <i
                      className="fa-solid fa-pen-to-square"
                      style={{ color: 'hsl(318, 22%, 27%)', cursor: 'pointer', }}
                      ></i>
                    </Button>

                    <Button size="small"
                    onClick={onDelete}
                    data-couponid={coupon?.couponID}
                    >
                      <i className="fa-solid fa-trash" style={{ color: 'hsl(318, 22%, 27%)' }}></i>
                    </Button>
                    
                  </CardActions>
                </Card>
              </Grid>
            ))
          }
        </Grid>
        ) : (
          <div>No coupons available</div>
      )}
      </Box>

      {showModal && selectedCoupon && (
        <div className='modal' onClick={() => setShowModal(false)}>
          <div className='modal-content' onClick={(e) => e.stopPropagation()}>
            <p>Are you sure you want to delete this coupon?</p>
            <div style={{ display:'flex', justifyContent:'end' }}>
              <Button variant="contained" onClick={confirmDelete} className='yes'>Yes</Button>
              <Button variant="text" onClick={() => setShowModal(false)} style={{color:'hsl(318, 22%, 27%)'}}>No</Button>
            </div>
          </div>
        </div>
      )}
    </>

  );
};

CouponListingByUser.propTypes = {
  couponLists: PropTypes.arrayOf(PropTypes.shape({})),
};

CouponListingByUser.defaultProps = {
  couponLists: coupons,
};

export default CouponListingByUser;
