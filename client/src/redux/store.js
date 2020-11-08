import rootReducer from "./reducer";
import { createStore, applyMiddleware } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";

const composeEnhancers = composeWithDevTools({
  trace: true,
});

const store = createStore(rootReducer, composeEnhancers());

export default store;
