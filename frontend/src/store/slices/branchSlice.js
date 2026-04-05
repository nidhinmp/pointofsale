import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchBranches = createAsyncThunk('branch/fetchBranches', async (storeId) => {
  const response = storeId 
    ? await api.get(`/branches/store/${storeId}`)
    : await api.get('/branches')
  return response.data
})

export const createBranch = createAsyncThunk('branch/createBranch', async (data) => {
  const response = await api.post('/branches', data)
  return response.data
})

const branchSlice = createSlice({
  name: 'branch',
  initialState: {
    branches: [],
    currentBranch: null,
    loading: false,
    error: null,
  },
  reducers: {
    setCurrentBranch: (state, action) => {
      state.currentBranch = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchBranches.pending, (state) => { state.loading = true })
      .addCase(fetchBranches.fulfilled, (state, action) => {
        state.loading = false
        state.branches = action.payload
      })
      .addCase(createBranch.fulfilled, (state, action) => {
        state.branches.push(action.payload)
      })
  },
})

export const { setCurrentBranch } = branchSlice.actions
export default branchSlice.reducer