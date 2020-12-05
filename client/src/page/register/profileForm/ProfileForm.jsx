import React, { useState, useEffect } from 'react'
import { Grid, Button, TextField } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'

import InputField from '../../../component/InputField/InputField'

import { login } from '../../../service/user'

import './ProfileForm.scss'

export default function ProfileForm({ user }) {
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

  return (
    <Grid container className="profile-form-container">
      <Grid item xs={12}>
        <Grid container className="form-container">
          <Grid item xs={12} className="title">
            Register
          </Grid>
          <Grid item xs={12}>
            <InputField
              label="email"
              variant="filled"
              name="username"
              value={username}
              error={!!error.username}
              onChange={(e) => inputOnChange('username', e)}
            />
          </Grid>
          <Grid item xs={12}>
            <InputField
              label="password"
              variant="filled"
              name="password"
              value={password}
              error={!!error.password}
              onChange={(e) => inputOnChange('password', e)}
            />
          </Grid>
          <Grid item xs={12}>
            <Button onClick={handleSubmit} color="primary" variant="contained">
              submit
            </Button>
          </Grid>
        </Grid>
      </Grid>
      <Grid item xs={12}>
        No account yet? Please <Link to="/register">Register</Link>
      </Grid>
    </Grid>
  )
}
