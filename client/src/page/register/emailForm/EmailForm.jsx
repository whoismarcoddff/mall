import React, { useState, useEffect } from 'react'
import { Grid, Button, TextField } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'

import InputField from '../../../component/InputField/InputField'

import './EmailForm.scss'

export default function EmailForm({ user }) {
  const history = useHistory()
  const [username, setUsername] = useState('')
  const [otp, setOtp] = useState('')
  const [error, setError] = useState({
    usename: '',
    otp: '',
  })

  const [validation, setValidation] = useState({
    usename: '',
    otp: '',
  })

  const inputOnChange = (name, e) => {
    switch (name) {
      case 'username':
        setUsername(e.target.value)
        break
      case 'otp':
        setOtp(e.target.value)
        break
      default:
        break
    }
  }

  const handleSubmit = () => {
    console.log('ooo submit')
  }

  return (
    <Grid container className="email-form-container">
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
              label="otp"
              variant="filled"
              name="otp"
              value={otp}
              error={!!error.otp}
              onChange={(e) => inputOnChange('otp', e)}
            />
          </Grid>
          <Grid item xs={12}>
            <Button onClick={handleSubmit} color="primary" variant="contained">
              submit
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  )
}