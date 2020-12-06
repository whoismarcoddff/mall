import React, { useState, useEffect } from 'react'
import { Grid, Button, Collapse } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'
import OtpInput from 'react-otp-input'

import InputField from '../../../component/InputField/InputField'

import './EmailForm.scss'

export default function EmailForm({ user }) {
  const history = useHistory()
  const [email, setEmail] = useState('')
  const [otp, setOtp] = useState('')
  const [mode, setMode] = useState('email')
  const [label, setLabel] = useState('verify')

  const [error, setError] = useState({
    email: '',
    otp: '',
  })

  const [validation, setValidation] = useState({
    usename: '',
    otp: '',
  })

  useEffect(() => {
    if (mode === 'email') {
      setLabel('verify')
    } else if (mode === 'otp') {
      setLabel('submit')
    }
  }, [mode])

  const inputOnChange = (name, e) => {
    switch (name) {
      case 'email':
        setEmail(e.target.value)
        break
      case 'otp':
        setOtp(e.target.value)
        break
      default:
        break
    }
  }

  const handleSubmit = () => {
    if (mode === 'email') {
      console.log('ooo request otp')
      if (true) {
        setMode('otp')
      }
    } else if (mode === 'otp') {
      console.log('ooo verify otp')
    }
  }

  return (
    <Grid container className="email-form-container">
      <Grid item xs={12}>
        <Grid container className="form-container">
          <Grid item xs={12} className="email-row">
            <InputField
              label="email"
              variant="filled"
              name="email"
              value={email}
              error={!!error.email}
              onChange={(e) => inputOnChange('email', e)}
            />
          </Grid>

          <Grid item xs={12} className="otp-row">
            <Collapse in={mode === 'otp'}>
              <Grid item xs={12}>
                <Grid container justify="center">
                  <OtpInput
                    value={otp}
                    onChange={(otp) => setOtp(otp)}
                    numInputs={6}
                    separator={<span>-</span>}
                  />
                </Grid>
              </Grid>
              <Grid item xs={12} className="count-down">
                Count Down: 60s
              </Grid>
            </Collapse>
          </Grid>

          <Grid item xs={12} className="submit-row">
            <Button onClick={handleSubmit} color="primary" variant="contained">
              {label}
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  )
}
