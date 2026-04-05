import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchUsers = createAsyncThunk('user/fetchUsers', async () => {
  const response = await api.get('/users')
  return response.data
})

export const createUser = createAsyncThunk('user/createUser', async (data) => {
  const response = await api.post('/users', data)
  return response.data
})

export const updateUser = createAsyncThunk('user/updateUser', async ({ id, data }) => {
  const response = await api.put(`/users/${id}`, data)
  return response.data
})

const userSlice = createSlice({
  name: 'user',
  initialState: {
    users: [],
    currentUser: null,
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state) => {
        state.loading = true
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.loading = false
        state.users = action.payload
      })
      .addCase(createUser.fulfilled, (state, action) => {
        state.users.push(action.payload)
      })
      .addCase(updateUser.fulfilled, (state, action) => {
        const index = state.users.findIndex(u => u.id === action.payload.id)
        if (index !== -1) state.users[index] = action.payload
      })
  },
})

export default userSlice.reducer