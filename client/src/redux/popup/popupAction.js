import * as popupType from "./popupType";

export const updateOtp = (otp) => {
  return {
    type: popupType.UPDATE_OTP,
    payload: otp,
  };
};

