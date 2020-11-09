import "./App.css";
import {
  Route,
  Switch,
  BrowserRouter as Router,
  Redirect,
} from "react-router-dom";

import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { setUser } from "./redux/user/userAction";

import Home from "./page/home/Home";
import Login from "./page/login/Login";
import User from "./page/user/User";
import NotFound from "./page/error/404";

function App(props) {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);

  useEffect(() => {
    console.log("ooo App");

    dispatch(setUser({ name: "test" }));
  }, []);

  return (
    <div className="App">
      <Router>
        <Switch>
          <Route exact path="/home">
            <Home />
          </Route>
          <Route exact path="/login">
            <Login user={user} />
          </Route>
          <Route path="/user">
            <User user={user} />
          </Route>
          <Route exact path="/">
            <Redirect to="/home" />
          </Route>
          <Route>
            <NotFound />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
