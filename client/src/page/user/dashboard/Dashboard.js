import React, { useEffect, useState } from "react";
import { Grid } from "@material-ui/core";

export default function Dashboard({ user }) {
  console.log("ooo dashboard");

  return (
    <Grid container className="dashboard">
      <Grid item xs={12}>
        Hi, {user ? user.name : null} welcome to dashboard...
      </Grid>
    </Grid>
  );
}
