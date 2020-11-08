import "./App.css";
import { Route, Switch, BrowserRouter as Router } from "react-router-dom";

import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { setUser } from "./redux/user/userAction";

import Home from "./page/home/Home";
import Login from "./page/login/Login";
import User from "./page/user/User";

function App(props) {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);

  useEffect(() => {
    console.log("ooo App");
    // dispatch(setUser({ name: "test" }));
    // console.log("ooo user", user);
  }, []);

  return (
    <Router>
      <div className="App">
        <Switch>
          <Route exact path="/home">
            <Home />
          </Route>
          <Route exact path="/login">
            <Login />
          </Route>
          <Route exact path="/user">
            <User user={user} />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
