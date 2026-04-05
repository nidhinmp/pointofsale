import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchOrders = createAsyncThunk('order/fetchOrders', async (branchId) => {
  const response = await api.get(`/orders/branch/${branchId}`)
  return response.data
})

export const createOrder = createAsyncThunk('order/createOrder', async (data) => {
  const response = await api.post('/orders', data)
  return response.data
})

export const updateOrderStatus = createAsyncThunk('order/updateOrderStatus', async ({ id, status }) => {
  const response = await api.put(`/orders/${id}/status?status=${status}`)
  return response.data
})

const orderSlice = createSlice({
  name: 'order',
  initialState: {
    orders: [],
    currentOrder: null,
    loading: false,
    error: null,
  },
  reducers: {
    setCurrentOrder: (state, action) => {
      state.currentOrder = action.payload
    },
    clearCart: (state) => {
      state.currentOrder = null
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchOrders.pending, (state) => { state.loading = true })
      .addCase(fetchOrders.fulfilled, (state, action) => {
        state.loading = false
        state.orders = action.payload
      })
      .addCase(createOrder.fulfilled, (state, action) => {
        state.orders.unshift(action.payload)
        state.currentOrder = null
      })
  },
})

export const { setCurrentOrder, clearCart } = orderSlice.actions
export default orderSlice.reducer