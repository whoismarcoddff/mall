import React from 'react'
import { Grid } from '@material-ui/core'
import { Link } from 'react-router-dom'

export default function Home() {
  return (
    <Grid className="home" container justify="center">
      <Grid item xs={12}>
        <h1>404 not found!!!</h1>
      </Grid>
      <Grid item xs={12}>
        <Link to="/">home</Link>
      </Grid>
    </Grid>
  )
}
