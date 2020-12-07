import { combineReducers } from 'redux'
import userReducer from './user/userReducer'
import popupReducer from './popup/popupReducer'

const rootReducer = combineReducers({
  user: userReducer,
  popup: popupReducer,
})

export default rootReducer
