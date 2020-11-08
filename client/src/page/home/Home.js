import React from "react";
import { Grid } from "@material-ui/core";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <Grid className="home" container>
      <Grid item xs={12}>Welcome to mall admin!</Grid>
      <Grid item xs={12}>
        <Link to="/user/profile">User</Link>
      </Grid>
    </Grid>
  );
}
