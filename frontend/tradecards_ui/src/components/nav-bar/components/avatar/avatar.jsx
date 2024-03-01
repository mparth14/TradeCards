import React, { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { Tooltip, IconButton, ListItemIcon } from '@mui/material';
import { getStorage } from '../../../../common-utils';
import PropTypes from 'prop-types';

const AvatarItem = (props) => {
  const { onSignout, className, onClickProfile, onClickMessages, } = props;

  const [anchorEl, setAnchorEl,] = useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const onLogout = () => {
    handleClose();
    onSignout();
  };

  const onViewProfile = () => {
    handleClose();
    onClickProfile();
  };

  const getFirstLetter = () => {
    const user = JSON.parse(getStorage('userInfo'));
    return user?.firstName?.[0];
  };

  return (
    <div className={className || ''}>
    <Tooltip title="Account settings">
      <IconButton
        onClick={handleClick}
        size="small"
        sx={{ ml: 2, }}
        aria-controls={open ? 'account-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
      >
        <Avatar sx={{ width: 32, height: 32, }}>{getFirstLetter()}</Avatar>
      </IconButton>
    </Tooltip>
    <Menu
      anchorEl={anchorEl}
      id="account-menu"
      open={open}
      onClose={handleClose}
      onClick={handleClose}
      PaperProps={{
        elevation: 0,
        sx: {
          overflow: 'visible',
          filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
          mt: 1.5,
          '& .MuiAvatar-root': {
            width: 32,
            height: 32,
            ml: -0.5,
            mr: 1,
          },
          '&:before': {
            content: '""',
            display: 'block',
            position: 'absolute',
            top: 0,
            right: 14,
            width: 10,
            height: 10,
            bgcolor: 'background.paper',
            transform: 'translateY(-50%) rotate(45deg)',
            zIndex: 0,
          },
        },
      }}
      transformOrigin={{ horizontal: 'right', vertical: 'top', }}
      anchorOrigin={{ horizontal: 'right', vertical: 'bottom', }}
    >
      <MenuItem onClick={onViewProfile}>
        <Avatar /> My account
      </MenuItem>
      <MenuItem
        onClick={onClickMessages}
        style={{ display: 'flex', gap: '20px', }}
      >
        <i className="fa-regular fa-message"></i>
        My Messages
      </MenuItem>
      <MenuItem onClick={onLogout}>
        <ListItemIcon>
          <i className="fa-solid fa-right-from-bracket"></i>
        </ListItemIcon>
        Logout
      </MenuItem>
    </Menu>
    </div>
  );
};

AvatarItem.propTypes = {
  onSignout: PropTypes.func.isRequired,
  className: PropTypes.string,
  onClickProfile: PropTypes.func.isRequired,
  onClickMessages: PropTypes.func.isRequired,
};
AvatarItem.defaultProps = { className: '', };

export default AvatarItem;
