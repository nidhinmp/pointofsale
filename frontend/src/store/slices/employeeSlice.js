import { createSlice } from '@reduxjs/toolkit'

const employeeSlice = createSlice({
  name: 'employee',
  initialState: { employees: [], loading: false },
  reducers: {
    setEmployees: (state, action) => { state.employees = action.payload },
  },
})

export const { setEmployees } = employeeSlice.actions
export default employeeSlice.reducer