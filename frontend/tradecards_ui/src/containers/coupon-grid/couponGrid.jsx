import React, { useEffect, useState } from 'react';
import { Box, Button, Card, CardActions, CardContent, CardMedia, CircularProgress, Grid, Typography } from '@mui/material';
import NavBar from '../../components/nav-bar';
import { convertBase64toImage } from '../../common-utils';
import { getAllCoupons, getCouponsByCategory } from '../home/apiUtils';
import { useLocation, useNavigate } from 'react-router-dom';
import EmptyState from '../../components/empty-state/emptyState';

const CouponGrid = () => {
  const [couponsData, setCouponsData,] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const categoryId = searchParams.get('category');
    setCouponsData(null);
    if (categoryId) {
      getCouponsByCategory({ categoryId, })
        .then((res) => setCouponsData(res));
    } else {
      getAllCoupons()
        .then((res) => setCouponsData(res));
    }
  }, [location.search,]);

  return (
    <div className='coupon-grid'>
      <NavBar/>
      <div className='coupon-grid-list'>
        <Box sx={{ flexGrow: 1, height: '100%', }}>
          <Grid
            container
            spacing={{ xs: 3, md: 3, }}
            columns={{ xs: 4, sm: 8, md: 12, }}
            sx={{ display: 'flex', marginLeft: '20px !important', height: '100%', }}
          >
            {
              !couponsData
                ? (
                <div style={{ width: '100%', height: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', }}>
                  <CircularProgress
                    color="secondary"
                  />
                </div>
                  )
                : (
                <>
                  {
                    !couponsData?.length
                      ? <EmptyState />
                      : (
                          couponsData?.map((coupon) => (
                        <Grid
                          item
                          key={coupon?.couponID || coupon?.couponName}
                          onClick={() => navigate(`/coupon-detail/${coupon?.couponID}`)}
                        >
                          <Card sx={{ maxWidth: 300, }}>
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
                                    <i
                                      className="fa-solid fa-location-dot"
                                    ></i>
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
                        )
                  }
                </>
                  )
            }
          </Grid>
        </Box>
      </div>
    </div>
  );
};

export default CouponGrid;
