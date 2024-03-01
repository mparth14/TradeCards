import React from 'react';
import EmptyStateIcon from '../../assets/empty-state.svg';
import './style.scss';

const EmptyState = () => {
  return (
    <div className='empty-state'>
      <img
        src={EmptyStateIcon}
        alt='Empty State'
      />
      <div>No Coupons to Display</div>
    </div>
  );
};

export default EmptyState;
