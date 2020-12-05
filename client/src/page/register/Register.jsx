import React, { useState, useEffect } from 'react'
import { useLocation } from 'react-router'
import { Grid } from '@material-ui/core'
import {
  useHistory,
  Link,
  Route,
  useRouteMatch,
  Redirect,
} from 'react-router-dom'

import ProfileForm from './profileForm/ProfileForm'
import EmailForm from './emailForm/EmailForm'
import AddressForm from './addressForm/AddressForm'
import MallStepper from '../../component/mallStepper/MallStepper'

import { login } from '../../service/user'

import './Register.scss'

export default function Login({ user }) {
  const location = useLocation()
  const history = useHistory()
  const { path } = useRouteMatch()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [pathname, setPathname] = useState('email')
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

  useEffect(() => {
    const arr = location.pathname.split('/')
    const pathname = arr[arr.length - 1]
    setPathname(pathname)
  }, [location])

  return (
    <Grid container className="register-container">
      <Grid item xs={12}>
        <MallStepper pathname={pathname} />
      </Grid>
      <Grid item xs={12}>
        <Route exact path={`${path}`}>
          <Redirect to={`${path}/email`} />
        </Route>
        <Route exact path={`${path}/email`}>
          <EmailForm />
        </Route>
        <Route path={`${path}/profile`}>
          <ProfileForm />
        </Route>
        <Route path={`${path}/address`}>
          <AddressForm />
        </Route>
      </Grid>
      <Grid item xs={12}>
        Already have an account? Please <Link to="/login">Login</Link>
      </Grid>
    </Grid>
  )
}
