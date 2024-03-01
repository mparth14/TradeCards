import React from 'react';
import Grid from '@mui/material/Grid';
import { Box, Button, Card, CardActions, CardContent, CardMedia, Typography } from '@mui/material';
import PropTypes from 'prop-types';
import { coupons } from './constants';
import { NavLink, useNavigate } from 'react-router-dom';
import { convertBase64toImage } from '../../common-utils';

const CouponListing = (props) => {
  const {
    heading,
    couponLists,
    isMoreCouponsAvailable,
  } = props;

  const navigate = useNavigate();

  return (
    <>
      <div className='coupon-listing-header'>
        <div className='coupon-listing-header-heading'>{heading}</div>
        <div>
          {
            isMoreCouponsAvailable
              ? (
              <NavLink
                to="/coupon-listing"
                className="coupon-listing-header-see-more"
              >
                See all
              </NavLink>
                )
              : null
          }
        </div>
      </div>
      <Box sx={{ flexGrow: 1, }}>
        <Grid
          container
          spacing={{ xs: 2, md: 3, }}
          columns={{ xs: 4, sm: 8, md: 12, }}
        >
          {
            couponLists?.slice(0, 5)?.map((coupon) => (
              <Grid
                item
                key={coupon?.couponID || coupon?.couponName}
                onClick={() => navigate(`/coupon-detail/${coupon?.couponID}`)}
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
                      coupon?.couponLocation
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
                  <CardActions>
                    <Button size="small">{coupon.userName}</Button>
                    <Button size="small">More Info</Button>
                  </CardActions>
                </Card>
              </Grid>
            ))
          }
        </Grid>
      </Box>
    </>

  );
};

CouponListing.propTypes = {
  heading: PropTypes.string,
  couponLists: PropTypes.arrayOf(PropTypes.shape({})),
  isMoreCouponsAvailable: PropTypes.bool,
};

CouponListing.defaultProps = {
  heading: 'Recommended Coupons',
  couponLists: coupons,
  isMoreCouponsAvailable: true,
};

export default CouponListing;
