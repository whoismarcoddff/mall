const initialState = { name: "test123" };

export default function userReducer(state = initialState, action) {
  switch (action.type) {
    case "SET_USER":
      return action.payload;

    case "RESET_USER":
      return null;

    default:
      return state;
  }
}
