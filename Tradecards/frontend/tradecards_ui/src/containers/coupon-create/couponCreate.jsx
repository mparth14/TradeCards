/* eslint-disable import/no-extraneous-dependencies */
import { Button, MenuItem, Select } from '@mui/material';
import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import { onCouponCreate, onCouponEdit } from './apiUtils';
import { useNavigate, useParams } from 'react-router-dom';
import InputHolder from '../login/components/input';
import { toast } from 'react-toastify';

function CouponCreate (props) {
  const { isEdit, } = props;

  // const REACT_APP_END_POINT_PROD = 'http://localhost:8080';
  const REACT_APP_END_POINT_PROD = 'http://csci5308vm13.research.cs.dal.ca:8080';

  const [couponTitle, setCouponTitle,] = useState('');
  const [couponDescription, setCouponDescription,] = useState('');
  const [couponVendor, setCouponVendor,] = useState('');
  const [couponCategorySelect, setCouponCategorySelect,] = useState([]);
  const [couponCategory, setCouponCategory,] = useState('Grocery');
  const [couponCategoryId, setCouponCategoryId,] = useState(null);
  const [couponValue, setCouponValue,] = useState('');
  const [couponPrice, setCouponPrice,] = useState('');
  const [couponValidity, setCouponValidity,] = useState('');
  const [couponLocation, setCouponLocation,] = useState('');
  const [couponListingDate, setCouponListingDate,] = useState('');
  const [couponImage, setCouponImage,] = useState('');
  const [couponType, setCouponType,] = useState(true);

  const { id, } = useParams();

  const couponValueNumber = Number(couponValue);
  const couponPriceNumber = Number(couponPrice);
  const couponCategoryIdNumber = Number(couponCategoryId);
  const sold = false;
  const user = JSON.parse(localStorage.getItem('userInfo'));
  const userId = user.userId;

  // Call on both forms
  useEffect(() => {
    const user2 = JSON.parse(localStorage.getItem('userInfo'));
    const fetchData = async () => {
      try {
        const response = await fetch(REACT_APP_END_POINT_PROD + '/api/categories', {
          headers: {
            Authorization: `Bearer ${user2.token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        setCouponCategorySelect(data);
        if (data.length > 0) {
          const defaultCategory = data[0];
          setCouponCategory(defaultCategory.categoryName);
          setCouponCategoryId(defaultCategory.categoryID);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  // Call only on create coupon form
  useEffect(() => {
    const currentDate = new Date();
    const formattedDate = currentDate.toISOString().slice(0, 10);
    setCouponListingDate(formattedDate);
  }, [!isEdit,]);

  // Call only on edit coupon form
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('userInfo'));
    const fetchData = async () => {
      try {
        const response = await fetch(REACT_APP_END_POINT_PROD + '/api/coupon/get-coupon/' + id, {
          headers: {
            Authorization: `Bearer ${user.token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const couponDetails = await response.json();
        setCouponTitle(couponDetails.couponName);
        setCouponDescription(couponDetails.couponDesc);
        setCouponVendor(couponDetails.couponBrand);
        setCouponCategory(couponDetails.couponCategory);
        setCouponCategoryId(couponDetails.categoryID);
        setCouponValue(couponDetails.couponValue.toString());
        setCouponPrice(couponDetails.couponSellingPrice.toString());
        const validity = new Date(couponDetails.expiryDate);
        setCouponValidity(validity.toISOString().slice(0, 10));
        setCouponLocation(couponDetails.couponLocation);
        const listingDate = new Date(couponDetails.couponListingDate);
        setCouponListingDate(listingDate.toISOString().slice(0, 10));
        setCouponType(couponDetails.online);
        setCouponImage(couponDetails.couponImage);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    if (isEdit) fetchData();
  }, [isEdit,]);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        const base64String = event.target.result.replace(/^data:image\/[a-z]+;base64,/, '');
        setCouponImage(base64String);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleCategoryChange = (event) => {
    const selectedCategory = couponCategorySelect.find(
      (coupon) => coupon.categoryName === event.target.value
    );

    if (selectedCategory) {
      const { categoryID, categoryName, } = selectedCategory;
      setCouponCategory(categoryName);
      setCouponCategoryId(categoryID);
    }
  };

  const navigate = useNavigate();

  const onSubmit = () => {
    if (isEdit) {
      onCouponEdit(couponTitle, couponDescription, couponVendor, couponValidity,
        couponValueNumber, couponPriceNumber, sold, couponType, couponCategory,
        couponListingDate, couponLocation, userId, couponCategoryIdNumber, couponImage, id)
        .then((res) => {
          if (res) {
            navigate('/user-profile');
            toast.success('Coupon edited successfully!');
          }
        });
    } else {
      onCouponCreate(couponTitle, couponDescription, couponVendor, couponValidity,
        couponValueNumber, couponPriceNumber, sold, couponType, couponCategory,
        couponListingDate, couponLocation, userId, couponCategoryIdNumber, couponImage)
        .then((res) => {
          if (res) {
            navigate('/home');
            toast.success('Coupon created successfully!');
          }
        });
    };
  };

  const onNavigate = () => {
    navigate('/home');
  };

  return (
    <div className='createCoupon-wrapper'>
     <div className='createCoupon'>
      { isEdit
        ? (<div className='createCoupon-heading'>Edit your coupon!</div>)
        : (<div className='createCoupon-heading'>Post a coupon!</div>)
      }

      <table>
        <tbody>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Coupon Name:</label>
            </td>
            <td>
              <InputHolder
                className='createCoupon-input'
                value={couponTitle}
                onChange={setCouponTitle}
                placeholder="Coupon Name"
              />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Description:</label>
            </td>
            <td>
              <InputHolder
                className='createCoupon-input'
                value={couponDescription}
                onChange={setCouponDescription}
                placeholder="Description"
              />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Coupon Vendor:</label>
            </td>
            <td>
              <InputHolder
                className='createCoupon-input'
                value={couponVendor}
                onChange={setCouponVendor}
                placeholder="Vendor"
              />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '28px', }}>Category</label>
            </td>
            <td>
            <Select
              className='createCoupon-dropdown'
              id='couponCategorySelect'
              labelId='couponCategorySelectLabel'
              value={couponCategory}
              onChange={handleCategoryChange}
              label="Category"
              defaultValue='Grocery'
            >
            {couponCategorySelect.map((coupon) => (
            <MenuItem
              key={coupon.categoryID}
              value={coupon.categoryName}>
                {coupon.categoryName}
            </MenuItem>
            ))}
            </Select>
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '28px', }}>Coupon Type</label>
            </td>
            <td>
            <Select
              className='createCoupon-dropdown'
              id='couponTypeSelect'
              labelId='couponTypeSelectLabel'
              value={couponType}
              onChange={e => setCouponType(e.target.value)}
              label="Category"
            >
              <MenuItem value={true}>Online</MenuItem>
              <MenuItem value={false}>Offline</MenuItem>
            </Select>
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Coupon Value:</label>
            </td>
            <td>
            <InputHolder
              className='createCoupon-input'
              value={couponValue}
              onChange={setCouponValue}
              placeholder="Original Value"
            />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Coupon Price:</label>
            </td>
            <td>
            <InputHolder
              className='createCoupon-input'
              value={couponPrice}
              onChange={setCouponPrice}
              placeholder="Selling price"
            />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Expiry Date:</label>
            </td>
            <td>
            <InputHolder
              className='createCoupon-input'
              value={couponValidity}
              onChange={setCouponValidity}
              type="date"
              placeholder="Validity"
            />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Coupon Location:</label>
            </td>
            <td>
            <InputHolder
              className='createCoupon-input'
              value={couponLocation}
              onChange={setCouponLocation}
              placeholder="Location"
            />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Listing Date:</label>
            </td>
            <td>
            <InputHolder
              className='createCoupon-input'
              value={couponListingDate}
              onChange={setCouponListingDate}
              readOnly={true}
              placeholder="Lisiting date"
            />
            </td>
          </tr>

          <tr>
            <td style={{ display: 'flex', alignItems: 'center', }}>
              <label style={{ marginRight: '10px', marginTop: '22px', }}>Upload Photo!</label>
            </td>
            <td>
            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              placeholder="Image"
              style={{ marginRight: '10px', marginTop: '22px', }}
            />
            </td>
          </tr>
        </tbody>
      </table>

        <Button
          variant="contained"
          onClick={onSubmit}
          className='createCoupon-submit'
        >
          Submit
        </Button>
        <div
          onClick={() => onNavigate()}
          className='createCoupon-switch'
        >
          {`${'Go back to Home'}`}
        </div>

     </div>
    </div>
  );
}

CouponCreate.propTypes = { isEdit: PropTypes.bool, };
CouponCreate.defaultProps = { isEdit: false, };

export default CouponCreate;
