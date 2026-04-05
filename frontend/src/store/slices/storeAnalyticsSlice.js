import { createSlice } from '@reduxjs/toolkit'

const storeAnalyticsSlice = createSlice({
  name: 'storeAnalytics',
  initialState: { data: null, loading: false },
  reducers: { setStoreAnalytics: (state, action) => { state.data = action.payload } },
})

export const { setStoreAnalytics } = storeAnalyticsSlice.actions
export default storeAnalyticsSlice.reducer