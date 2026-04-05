import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchInventory = createAsyncThunk('inventory/fetchInventory', async (branchId) => {
  const response = await api.get(`/inventory/branch/${branchId}`)
  return response.data
})

export const fetchLowStock = createAsyncThunk('inventory/fetchLowStock', async (branchId) => {
  const response = await api.get(`/inventory/branch/${branchId}/low-stock`)
  return response.data
})

const inventorySlice = createSlice({
  name: 'inventory',
  initialState: {
    items: [],
    lowStockItems: [],
    loading: false,
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchInventory.pending, (state) => { state.loading = true })
      .addCase(fetchInventory.fulfilled, (state, action) => {
        state.loading = false
        state.items = action.payload
      })
      .addCase(fetchLowStock.fulfilled, (state, action) => {
        state.lowStockItems = action.payload
      })
  },
})

export default inventorySlice.reducer