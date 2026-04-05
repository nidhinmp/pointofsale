import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchCustomers = createAsyncThunk('customer/fetchCustomers', async (storeId) => {
  const response = await api.get(`/customers/store/${storeId}`)
  return response.data
})

export const createCustomer = createAsyncThunk('customer/createCustomer', async (data) => {
  const response = await api.post('/customers', data)
  return response.data
})

const customerSlice = createSlice({
  name: 'customer',
  initialState: {
    customers: [],
    selectedCustomer: null,
    loading: false,
    error: null,
  },
  reducers: {
    setSelectedCustomer: (state, action) => {
      state.selectedCustomer = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCustomers.pending, (state) => { state.loading = true })
      .addCase(fetchCustomers.fulfilled, (state, action) => {
        state.loading = false
        state.customers = action.payload
      })
      .addCase(createCustomer.fulfilled, (state, action) => {
        state.customers.push(action.payload)
      })
  },
})

export const { setSelectedCustomer } = customerSlice.actions
export default customerSlice.reducer