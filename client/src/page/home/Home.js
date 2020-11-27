import React, { useState, useEffect } from 'react'
import { Grid } from '@material-ui/core'
import { Link } from 'react-router-dom'

export default function Home() {
  const [data, setData] = useState(null)

  useEffect(() => {
    // const res = getHello();
    // console.log('ooo',res)
    // setData(res)
  }, [])

  return (
    <Grid className="home" container justify="center">
      <Grid item>Welcome to mall admin!</Grid>
      <Grid item>
        <Link to="/user/profile">User</Link>
      </Grid>
    </Grid>
  )
}
