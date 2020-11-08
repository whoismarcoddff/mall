import React, { useEffect, useState } from "react";
import { Grid } from "@material-ui/core";

export default function Profile({ user }) {
  useEffect(() => {
    console.log("ooo profile", user);
  }, []);
  return (
    <Grid container className="profile">
      <Grid item xs={12}>
        {user.name}
      </Grid>
    </Grid>
  );
}
