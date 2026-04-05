import { createSlice } from '@reduxjs/toolkit'

const subscriptionSlice = createSlice({
  name: 'subscription',
  initialState: { plans: [], currentPlan: null },
  reducers: { setPlans: (state, action) => { state.plans = action.payload } },
})

export const { setPlans } = subscriptionSlice.actions
export default subscriptionSlice.reducer