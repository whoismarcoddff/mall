import * as userType from "./userType";

export const setUser = (user) => {
  return {
    type: userType.SET_USER,
    payload: user,
  };
};

export const resetUser = () => {
  return {
    type: userType.RESET_USER,
    payload: null,
  };
};
