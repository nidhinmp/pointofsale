import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const login = createAsyncThunk('auth/login', async (credentials) => {
  const response = await api.post('/auth/login', credentials)
  return response.data
})

export const register = createAsyncThunk('auth/register', async (data) => {
  const response = await api.post('/auth/register', data)
  return response.data
})

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: null,
    token: localStorage.getItem('token'),
    isAuthenticated: !!localStorage.getItem('token'),
    role: null,
    loading: false,
    error: null,
  },
  reducers: {
    logout: (state) => {
      state.user = null
      state.token = null
      state.isAuthenticated = false
      state.role = null
      localStorage.removeItem('token')
    },
    setCredentials: (state, action) => {
      state.user = action.payload.user
      state.token = action.payload.token
      state.isAuthenticated = true
      state.role = action.payload.role
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(login.fulfilled, (state, action) => {
        state.loading = false
        state.user = action.payload
        state.token = action.payload.token
        state.isAuthenticated = true
        state.role = action.payload.role
        localStorage.setItem('token', action.payload.token)
      })
      .addCase(login.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message
      })
      .addCase(register.fulfilled, (state, action) => {
        state.user = action.payload
        state.token = action.payload.token
        state.isAuthenticated = true
        state.role = action.payload.role
        localStorage.setItem('token', action.payload.token)
      })
  },
})

export const { logout, setCredentials } = authSlice.actions
export default authSlice.reducer