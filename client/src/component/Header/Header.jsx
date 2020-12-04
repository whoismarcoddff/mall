import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { Grid, Hidden } from '@material-ui/core'
import { Link } from 'react-router-dom'

import './Header.scss'

export default function Header() {
  return (
    <Grid container className="header-container" alignItems="center">
      <Hidden mdUp>
        <Grid item xs={12}>
          mobile header
        </Grid>
      </Hidden>
      <Hidden smDown>
        <Grid item xs={12}>
          tablet/desktop header
        </Grid>
      </Hidden>
    </Grid>
  )
}
