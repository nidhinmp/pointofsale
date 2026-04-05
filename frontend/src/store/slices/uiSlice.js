import { createSlice } from '@reduxjs/toolkit'

const uiSlice = createSlice({
  name: 'ui',
  initialState: { sidebarOpen: true, notifications: [] },
  reducers: {
    toggleSidebar: (state) => { state.sidebarOpen = !state.sidebarOpen },
    addNotification: (state, action) => { state.notifications.push(action.payload) },
  },
})

export const { toggleSidebar, addNotification } = uiSlice.actions
export default uiSlice.reducer