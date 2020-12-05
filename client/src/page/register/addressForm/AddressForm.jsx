import React, { useState, useEffect } from 'react'
import { Grid, Button, TextField } from '@material-ui/core'
import { useHistory, Link } from 'react-router-dom'

import InputField from '../../../component/InputField/InputField'

import './AddressForm.scss'

export default function AddressForm({ user }) {
  const history = useHistory()
  const [address, setAddress] = useState('')

  const [error, setError] = useState({
    address: '',
  })

  const [validation, setValidation] = useState({
    address: '',
  })

  const inputOnChange = (name, e) => {
    switch (address) {
      case 'address':
        setAddress(e.target.value)
        break
      default:
        break
    }
  }

  const handleSubmit = () => {
    console.log('ooo address')
  }

  return (
    <Grid container className="address-form-container">
      <Grid item xs={12}>
        <Grid container className="form-container">
          <Grid item xs={12} className="title">
            Address
          </Grid>
          <Grid item xs={12}>
            <InputField
              label="address"
              variant="filled"
              name="address"
              value={address}
              error={!!error.address}
              onChange={(e) => inputOnChange('address', e)}
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
