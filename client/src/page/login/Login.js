import React, { useState, useEffect } from 'react'
import { Grid, TextField, Button } from '@material-ui/core'
import { useHistory } from 'react-router-dom'

import { login } from '../../service/user'

export default function Login({ user }) {
  const history = useHistory()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState({
    usename: '',
    password: '',
  })

  const [validation, setValidation] = useState({
    usename: '',
    password: '',
  })

  const inputOnChange = (name, e) => {
    switch (name) {
      case 'username':
        setUsername(e.target.value)
        break
      case 'password':
        setPassword(e.target.value)
        break
      default:
        break
    }
  }

  const handleSubmit = () => {
    const user = login(username, password)
    console.log('ooo', user.status)
  }

  useEffect(() => {
    if (user) {
      history.push('/user')
    }
  }, [])

  return (
    <Grid container className="login-container">
      <Grid item xs={12} className="title">
        Login
      </Grid>
      <Grid item xs={12}>
        <TextField
          label="username"
          variant="outlined"
          name="username"
          value={username}
          error={error.username}
          onChange={(e) => inputOnChange('username', e)}
        />
      </Grid>
      <Grid item xs={12}>
        <TextField
          label="password"
          variant="outlined"
          name="password"
          value={password}
          error={error.password}
          onChange={(e) => inputOnChange('password', e)}
        />
      </Grid>
      <Grid item xs={12}>
        <Button onClick={handleSubmit} color="primary" variant="contained">
          submit
        </Button>
      </Grid>
    </Grid>
  )
}
