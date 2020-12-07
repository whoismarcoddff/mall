import React, { useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { Grid, Button, Collapse } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'
import OtpInput from 'react-otp-input'

import { updateOtp } from '../../../redux/popup/popupAction'

import InputField from '../../../component/InputField/InputField'
import Otp from '../../../component/popup/otp/Otp'

import './EmailForm.scss'

export default function EmailForm({ user }) {
  const history = useHistory()
  const dispatch = useDispatch()

  const [email, setEmail] = useState('')
  const [error, setError] = useState({
    email: '',
    otp: '',
  })
  const [validation, setValidation] = useState({
    usename: '',
    otp: '',
  })

  const inputOnChange = (name, e) => {
    switch (name) {
      case 'email':
        setEmail(e.target.value)
        break
      default:
        break
    }
  }

  const handleRequest = () => {
    if (true) {
      dispatch(updateOtp({ show: true, email: email, countdown: 30 }))
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

          <Grid item xs={12} className="submit-row">
            <Button onClick={handleRequest} color="primary" variant="contained">
              Request
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  )
}
