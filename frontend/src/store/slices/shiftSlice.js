import { createSlice } from '@reduxjs/toolkit'

const shiftSlice = createSlice({
  name: 'shift',
  initialState: { currentShift: null, shiftReport: null, loading: false },
  reducers: { setCurrentShift: (state, action) => { state.currentShift = action.payload } },
})

export const { setCurrentShift } = shiftSlice.actions
export default shiftSlice.reducer