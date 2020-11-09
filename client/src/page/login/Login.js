import React from "react";
import { Grid } from "@material-ui/core";
import { useHistory } from "react-router-dom";

export default function Login({ user }) {
  const history = useHistory();
  console.log("ooo login");

  if (user) {
    history.push("/user");
  }
  return (
    <Grid container>
      <Grid item xs={12}>
        Please Login...
      </Grid>
    </Grid>
  );
}
