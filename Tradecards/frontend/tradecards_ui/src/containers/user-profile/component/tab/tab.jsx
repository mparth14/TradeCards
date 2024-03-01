import React, { useState } from 'react';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';

const TabItem = () => {
  const [value, setValue,] = useState('one');

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
        <Box sx={{ width: '100%', display: 'flex', justifyContent: 'center', }}>
          <Tabs
            value={value}
            onChange={handleChange}
            textColor="secondary"
            indicatorColor="secondary"
            aria-label="secondary tabs example"
          >
            <Tab value="one"
              label="My coupons" />
          </Tabs>
        </Box>
  );
};
export default TabItem;
