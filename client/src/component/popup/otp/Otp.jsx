import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Grid, Button, Collapse, Modal } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'
import OtpInput from 'react-otp-input'
import useCounter from '@react-hook/counter'

import { updateOtp } from '../../../redux/popup/popupAction'

import './Otp.scss'

export default function Otp() {
  const dispatch = useDispatch()
  const otp = useSelector((state) => state.popup.otp)

  const counter = useCounter(otp.countdown, {
    min: 0,
    max: otp.countdown,
  })

  const [otpValue, setOtpValue] = useState('')

  const handleVerify = () => {
    console.log('ooo verify')
  }

  useEffect(() => {
    counter.decr()
  }, [])

  return (
    <Modal
      className="otp-modal"
      open={otp.show}
      onClose={() => dispatch(updateOtp({ show: false }))}
      aria-labelledby="simple-modal-title"
      aria-describedby="simple-modal-description"
    >
      <Grid container className="otp-modal-container">
        <Grid item xs={12} className="otp-row">
          <Grid container justify="center">
            <OtpInput
              value={otpValue}
              onChange={(otpValue) => setOtpValue(otpValue)}
              numInputs={6}
              separator={<span>-</span>}
            />
          </Grid>
        </Grid>
        <Grid item xs={12} className="count-down">
          Count Down: {counter.value}s
        </Grid>
        <Grid item xs={12} className="submit-row">
          <Button onClick={handleVerify} color="primary" variant="contained">
            Verify
          </Button>
        </Grid>
      </Grid>
    </Modal>
  )
}
