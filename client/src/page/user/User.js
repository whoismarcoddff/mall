import React, { useEffect, useState } from "react";
import { Grid } from "@material-ui/core";
import { useSelector } from "react-redux";
import { Redirect, useHistory } from "react-router";
import {
  Route,
  Switch,
  BrowserRouter as Router,
  useRouteMatch,
} from "react-router-dom";

import Dashboard from "./dashboard/Dashboard";
import Profile from "./profile/Profile";

export default function User({ user }) {
  let { path } = useRouteMatch();

  useEffect(() => {
    console.log('ooo user')
  }, [])

  return (
    <Grid container className="user">
      <Router>
        <Switch>
          <Route exact path={`${path}`}>
            <Redirect to={`${path}/profile`} />
          </Route>
          <Route exact path={`${path}/dashboard`}>
            <Dashboard user={user} />
          </Route>
          <Route exact path={`${path}/profile`}>
            <Profile user={user} />
          </Route>
        </Switch>
      </Router>
    </Grid>
  );
}
