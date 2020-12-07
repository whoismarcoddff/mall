const initialState = {
  otp: { show: false, email: '', countdown: 0 },
}

export default function userReducer(state = initialState, action) {
  switch (action.type) {
    case 'UPDATE_OTP':
      return { ...initialState, otp: action.payload }
    default:
      return state
  }
}
