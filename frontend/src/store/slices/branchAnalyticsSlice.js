import { createSlice } from '@reduxjs/toolkit'

const branchAnalyticsSlice = createSlice({
  name: 'branchAnalytics',
  initialState: { data: null, loading: false },
  reducers: { setAnalytics: (state, action) => { state.data = action.payload } },
})

export const { setAnalytics } = branchAnalyticsSlice.actions
export default branchAnalyticsSlice.reducer