// Star.js
import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar, faStarHalf } from '@fortawesome/free-solid-svg-icons';
import PropTypes from 'prop-types';

const Star = (props) => {
  const {
    filled,
    halfFilled,
  } = props;

  return <FontAwesomeIcon icon={halfFilled ? faStarHalf : faStar}
    color={(filled || halfFilled) ? 'gold' : 'gray'} />;
};

Star.propTypes = {
  filled: PropTypes.bool.isRequired,
  halfFilled: PropTypes.bool.isRequired,
};

export default Star;
