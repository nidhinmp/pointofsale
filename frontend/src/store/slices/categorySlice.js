import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchCategories = createAsyncThunk('category/fetchCategories', async (storeId) => {
  const response = await api.get(`/categories/store/${storeId}`)
  return response.data
})

const categorySlice = createSlice({
  name: 'category',
  initialState: {
    categories: [],
    loading: false,
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCategories.pending, (state) => { state.loading = true })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.loading = false
        state.categories = action.payload
      })
  },
})

export default categorySlice.reducer