import React from 'react';
import PropTypes from 'prop-types';
import { Input } from '@mui/material';

const InputHolder = (props) => {
  const {
    className,
    onChange,
    value,
    type,
    placeholder,
    ...params
  } = props;

  return (
    <Input
      type={type}
      value={value}
      onChange={(e) => onChange(e.target.value)}
      className={`login-input ${className}`}
      placeholder={placeholder}
      {...params}
    />
  );
};

InputHolder.propTypes = {
  className: PropTypes.string,
  onChange: PropTypes.func.isRequired,
  value: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  type: PropTypes.string,
};

InputHolder.defaultProps = {
  className: '',
  type: 'text',
  placeholder: '',
};

export default InputHolder;
