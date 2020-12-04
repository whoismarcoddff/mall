import React, { useState, useEffect } from 'react'
import { Grid, Button, TextField } from '@material-ui/core'
import { useHistory, Link, Route } from 'react-router-dom'

import InputField from '../../component/InputField/InputField'
import ProfileForm from './profileForm/ProfileForm'
import EmailForm from './emailForm/EmailForm'

import { login } from '../../service/user'

import './Login.scss'

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
    <Grid container className="register-container">
      <Grid item xs={12}>
        <Route exact path="/email">
          <EmailForm />
        </Route>
        <Route path="/profile">
          <ProfileForm />
        </Route>
      </Grid>
      <Grid item xs={12}>
        Have account? Please <Link to="/login">LOGIN</Link>
      </Grid>
    </Grid>
  )
}
