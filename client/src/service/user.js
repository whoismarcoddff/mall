import api from './config'

export const login = (username, password) => {
  return api.post('/login', { username, password })
}

export const getUsers = () => {
  return api.get('/users')
}
