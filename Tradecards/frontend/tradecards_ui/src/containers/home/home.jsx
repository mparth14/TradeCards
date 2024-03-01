import React, { useEffect, useState } from 'react';
import NavBar from '../../components/nav-bar';
import Slideshow from '../../components/banner/banner';
import CouponListing from '../../components/coupon-listing';
import { getAllCoupons } from './apiUtils';
import { convertBase64toImage } from '../../common-utils';
import { useNavigate } from 'react-router-dom';
import { CircularProgress } from '@mui/material';
import EmptyState from '../../components/empty-state/emptyState';

const Home = (props) => {
  const [couponsData, setCouponsData,] = useState();
  const navigate = useNavigate();
  const [slides, setSlides,] = useState([]);

  useEffect(() => {
    if (couponsData) {
      setSlides([
        ...couponsData?.map((couponData) => (
          {
            image: convertBase64toImage(couponData?.couponImage),
            onClick: () => navigate(`/coupon-detail/${couponData?.couponID}`),
          }
        )),
      ]);
    }
  }, [couponsData,]);

  useEffect(() => {
    getAllCoupons()
      .then((res) => setCouponsData(res));
  }, []);

  return (
    <div style={{ height: '100%', }}>
      <NavBar />
  {
    !couponsData
      ? <div style={{ width: '100%', height: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center', }}>
        <CircularProgress
          color="secondary"
        />
      </div>
      : (
        <>
          {
            couponsData?.length
              ? (
                  <>
                    <Slideshow
                      slides={slides?.slice(0, 5)}
                      interval={3000}
                    />
                    <div className='coupon-listing'>
                      <CouponListing
                        couponLists={couponsData}
                        isMoreCouponsAvailable={true}
                      />
                    </div>
                  </>
                )
              : <EmptyState />
          }
        </>
        )
  }
    </div>
  );
};

export default Home;
