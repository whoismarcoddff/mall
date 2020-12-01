import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { Grid } from '@material-ui/core'
import { Link } from 'react-router-dom'

export default function Home() {
  const user = useSelector((state) => state.user)

  useEffect(() => {
    // const res = getHello();
    // console.log('ooo',res)
    // setData(res)
  }, [])

  return (
    <Grid className="home" container justify="center">
      <Grid item>Welcome to mall admin!</Grid>
      <Grid item>
        {user ? (
          <Link to="/user/profile">User</Link>
        ) : (
          <Link to="/login">Login</Link>
        )}
      </Grid>
    </Grid>
  )
}
