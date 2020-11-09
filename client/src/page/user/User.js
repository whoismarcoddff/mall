import React, { useEffect, useState } from "react";
import { Grid } from "@material-ui/core";
import { Redirect, useHistory } from "react-router";
import { Route, useRouteMatch } from "react-router-dom";

import Dashboard from "./dashboard/Dashboard";
import Profile from "./profile/Profile";

export default function User({ user }) {
  const history = useHistory();
  let { path } = useRouteMatch();

  console.log("ooo user");

  useEffect(() => {
    if (!user) {
      history.push("/login");
    }
  }, []);

  return (
    <Grid container className="user">
      <Route exact path={`${path}`}>
        <Redirect to={`${path}/profile`} />
      </Route>
      <Route path={`${path}/dashboard`}>
        <Dashboard user={user} />
      </Route>
      <Route path={`${path}/profile`}>
        <Profile user={user} />
      </Route>
    </Grid>
  );
}
