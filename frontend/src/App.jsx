import { Routes, Route, Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import Login from './pages/Login'
import CashierTerminal from './pages/CashierTerminal'
import BranchDashboard from './pages/BranchDashboard'
import StoreAdmin from './pages/StoreAdmin'

function App() {
  const { isAuthenticated, role } = useSelector((state) => state.auth)

  if (!isAuthenticated) {
    return <Login />
  }

  return (
    <Routes>
      <Route path="/" element={
        role === 'CASHIER' ? <Navigate to="/cashier" /> :
        role === 'BRANCH_MANAGER' ? <Navigate to="/branch" /> :
        <Navigate to="/store-admin" />
      } />
      <Route path="/cashier" element={<CashierTerminal />} />
      <Route path="/branch" element={<BranchDashboard />} />
      <Route path="/store-admin" element={<StoreAdmin />} />
    </Routes>
  )
}

export default App