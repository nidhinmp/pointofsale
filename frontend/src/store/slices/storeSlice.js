import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchStores = createAsyncThunk('store/fetchStores', async () => {
  const response = await api.get('/stores')
  return response.data
})

export const createStore = createAsyncThunk('store/createStore', async (data) => {
  const response = await api.post('/stores', data)
  return response.data
})

const storeSlice = createSlice({
  name: 'store',
  initialState: {
    stores: [],
    currentStore: null,
    loading: false,
    error: null,
  },
  reducers: {
    setCurrentStore: (state, action) => {
      state.currentStore = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchStores.pending, (state) => { state.loading = true })
      .addCase(fetchStores.fulfilled, (state, action) => {
        state.loading = false
        state.stores = action.payload
      })
      .addCase(createStore.fulfilled, (state, action) => {
        state.stores.push(action.payload)
      })
  },
})

export const { setCurrentStore } = storeSlice.actions
export default storeSlice.reducer