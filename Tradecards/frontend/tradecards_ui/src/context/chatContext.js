import {
  createContext,
  useContext,
  useReducer,
  React
} from 'react';
import { AuthorizationContext } from './AuthorizationContext';
import PropTypes from 'prop-types';

const ChatContext = createContext();

const ChatContextProvider = ({ children, }) => {
  const { user, } = useContext(AuthorizationContext);
  const INITIAL_STATE = {
    chatId: 'null',
    user: {},
  };

  const chatReducer = (state, action) => {
    switch (action.type) {
      case 'CHANGE_USER':
        return {
          user: action.payload,
          chatId:
            user.uid > action.payload.uid
              ? user.uid + action.payload.uid
              : action.payload.uid + user.uid,
        };

      default:
        return state;
    }
  };

  const [state, dispatch,] = useReducer(chatReducer, INITIAL_STATE);

  return (
    <ChatContext.Provider value={{ data: state, dispatch, }}>
      {children}
    </ChatContext.Provider>
  );
};

ChatContextProvider.propTypes = { children: PropTypes.node, };

export {
  ChatContext,
  ChatContextProvider,
};
