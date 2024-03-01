import { TextField } from '@mui/material';
import React, { useState } from 'react';
import PropTypes from 'prop-types';

const SearchBar = (props) => {
  const { onSearch, } = props;

  const [search, setSearch,] = useState('');

  const onChange = (e) => {
    setSearch(e.target.value);
  };

  const onClickSearch = (e) => {
    if (e.key === 'Enter') {
      onSearch();
    }
  };

  return (
    <div className='search-bar'>
      <TextField
        label="Search"
        value={search}
        onChange={onChange}
        onKeyDown={onClickSearch}
        className='search-bar-text'
        size='small'
      />
    </div>
  );
};

SearchBar.propTypes = { onSearch: PropTypes.func, };

export default SearchBar;
