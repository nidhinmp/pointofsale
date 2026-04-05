import { createSlice } from '@reduxjs/toolkit'

const refundSlice = createSlice({
  name: 'refund',
  initialState: { refunds: [], loading: false },
  reducers: { setRefunds: (state, action) => { state.refunds = action.payload } },
})

export const { setRefunds } = refundSlice.actions
export default refundSlice.reducer