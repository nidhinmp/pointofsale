import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import api from '../../services/api'

export const fetchProducts = createAsyncThunk('product/fetchProducts', async (storeId) => {
  const response = await api.get(`/products/store/${storeId}`)
  return response.data
})

export const searchProducts = createAsyncThunk('product/searchProducts', async ({ storeId, query }) => {
  const response = await api.get(`/products/search?storeId=${storeId}&query=${query}`)
  return response.data
})

export const createProduct = createAsyncThunk('product/createProduct', async (data) => {
  const response = await api.post('/products', data)
  return response.data
})

const productSlice = createSlice({
  name: 'product',
  initialState: {
    products: [],
    selectedProduct: null,
    searchResults: [],
    loading: false,
    error: null,
  },
  reducers: {
    setSelectedProduct: (state, action) => {
      state.selectedProduct = action.payload
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => { state.loading = true })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false
        state.products = action.payload
      })
      .addCase(searchProducts.fulfilled, (state, action) => {
        state.searchResults = action.payload
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.products.push(action.payload)
      })
  },
})

export const { setSelectedProduct } = productSlice.actions
export default productSlice.reducer