// RatingStars.js
import React from 'react';
import Star from './components/star';
import PropTypes from 'prop-types';

const ReviewStars = (props) => {
  const { review, } = props;
  // const maxStars = Math.round(review) || 0;
  const filledStars = Math.floor(review) || 0;
  const hasHalfStar = review % 1 !== 0;

  const renderStars = () => (
    <div>
      (Rating - {[...Array(filledStars),]?.map((_, index) => (
        <Star
          key={index}
          filled={true}
          halfFilled={false}
        />
    ))}
      {hasHalfStar && (
      <Star
        filled={false}
        halfFilled={true}
      />)}
      {/* {[...Array(maxStars - filledStars - (hasHalfStar ? 1 : 0)),].map((_, index) => (
        <Star
          key={filledStars + index + 1}
          filled={false}
          halfFilled={false}
        />
      ))} */}
      )
    </div>
  );

  return <div>{review ? renderStars() : null}</div>;
};

ReviewStars.propTypes = { review: PropTypes.number.isRequired, };

export default ReviewStars;
