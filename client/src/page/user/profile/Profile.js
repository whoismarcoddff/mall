import React, { useEffect, useState } from "react";
import { Grid } from "@material-ui/core";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

export default function Profile({ user }) {
  console.log("ooo profile");

  return (
    <Grid container className="profile" justify="center">
      <Grid item>Hi, {user ? user.name : null}&nbsp;</Grid>
      <Grid item>
        Go to <Link to="/user/dashboard">dashboard</Link>
      </Grid>
    </Grid>
  );
}
