import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchBranches } from '../store/slices/branchSlice'
import { fetchOrders } from '../store/slices/orderSlice'
import { fetchInventory, fetchLowStock } from '../store/slices/inventorySlice'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts'
import { TrendingUp, DollarSign, ShoppingCart, Users, Package, AlertTriangle, LogOut } from 'lucide-react'

export default function BranchDashboard() {
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)
  const { branches, currentBranch } = useSelector((state) => state.branch)
  const { orders } = useSelector((state) => state.order)
  const { items: inventory, lowStockItems } = useSelector((state) => state.inventory)

  const [activeTab, setActiveTab] = useState('overview')

  useEffect(() => {
    dispatch(fetchBranches(1))
    dispatch(fetchOrders(1))
    dispatch(fetchInventory(1))
    dispatch(fetchLowStock(1))
  }, [dispatch])

  // Mock analytics data
  const analyticsData = {
    totalSales: 2450.50,
    totalOrders: 45,
    avgOrderValue: 54.45,
    completedOrders: 42,
    pendingOrders: 3,
    salesGrowth: 12.5,
    dailySales: [
      { date: 'Mon', sales: 320, orders: 8 },
      { date: 'Tue', sales: 450, orders: 12 },
      { date: 'Wed', sales: 380, orders: 10 },
      { date: 'Thu', sales: 520, orders: 14 },
      { date: 'Fri', sales: 680, orders: 18 },
      { date: 'Sat', sales: 450, orders: 12 },
      { date: 'Sun', sales: 280, orders: 7 },
    ],
    paymentMethodBreakdown: {
      CASH: 1200,
      CARD: 900,
      MOBILE: 350,
    },
  }

  const COLORS = ['#3B82F6', '#10B981', '#F59E0B']

  const stats = [
    { label: 'Total Sales', value: `$${analyticsData.totalSales.toFixed(2)}`, icon: DollarSign, color: '#3B82F6' },
    { label: 'Orders', value: analyticsData.totalOrders, icon: ShoppingCart, color: '#10B981' },
    { label: 'Avg Order', value: `$${analyticsData.avgOrderValue.toFixed(2)}`, icon: TrendingUp, color: '#F59E0B' },
    { label: 'Customers', value: 28, icon: Users, color: '#8B5CF6' },
  ]

  return (
    <div style={{ display: 'flex', minHeight: '100vh', background: '#F8FAFC' }}>
      {/* Sidebar */}
      <div style={{ width: '250px', background: 'white', borderRight: '1px solid #E2E8F0', display: 'flex', flexDirection: 'column' }}>
        <div style={{ padding: '20px', borderBottom: '1px solid #E2E8F0' }}>
          <h2 style={{ fontSize: '20px', fontWeight: '700', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Package size={24} color="#3B82F6" />
            Branch Dashboard
          </h2>
        </div>
        <nav style={{ flex: 1, padding: '16px' }}>
          {['overview', 'orders', 'inventory', 'products', 'customers'].map((tab) => (
            <button
              key={tab}
              onClick={() => setActiveTab(tab)}
              style={{
                display: 'block',
                width: '100%',
                padding: '12px 16px',
                marginBottom: '4px',
                borderRadius: '8px',
                textAlign: 'left',
                textTransform: 'capitalize',
                background: activeTab === tab ? '#3B82F6' : 'transparent',
                color: activeTab === tab ? 'white' : '#64748B',
                fontWeight: '500',
              }}
            >
              {tab}
            </button>
          ))}
        </nav>
        <div style={{ padding: '16px', borderTop: '1px solid #E2E8F0' }}>
          <div style={{ marginBottom: '16px', fontSize: '14px' }}>
            <p style={{ fontWeight: '600' }}>{user?.firstName} {user?.lastName}</p>
            <p style={{ color: '#64748B' }}>{user?.email}</p>
          </div>
          <button className="btn btn-outline" style={{ width: '100%' }} onClick={() => window.location.href = '/'}>
            <LogOut size={18} />
            Logout
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div style={{ flex: 1, padding: '24px' }}>
        {activeTab === 'overview' && (
          <>
            {/* Stats Grid */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '16px', marginBottom: '24px' }}>
              {stats.map((stat, idx) => (
                <div key={idx} className="card" style={{ padding: '20px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                    <div>
                      <p style={{ fontSize: '13px', color: '#64748B', marginBottom: '4px' }}>{stat.label}</p>
                      <p style={{ fontSize: '24px', fontWeight: '700' }}>{stat.value}</p>
                    </div>
                    <div style={{ padding: '10px', background: `${stat.color}15`, borderRadius: '8px' }}>
                      <stat.icon size={20} color={stat.color} />
                    </div>
                  </div>
                </div>
              ))}
            </div>

            {/* Charts */}
            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '16px', marginBottom: '24px' }}>
              <div className="card" style={{ padding: '20px' }}>
                <h3 style={{ marginBottom: '16px', fontWeight: '600' }}>Weekly Sales</h3>
                <ResponsiveContainer width="100%" height={250}>
                  <LineChart data={analyticsData.dailySales}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" />
                    <YAxis />
                    <Tooltip />
                    <Line type="monotone" dataKey="sales" stroke="#3B82F6" strokeWidth={2} />
                  </LineChart>
                </ResponsiveContainer>
              </div>
              <div className="card" style={{ padding: '20px' }}>
                <h3 style={{ marginBottom: '16px', fontWeight: '600' }}>Payment Methods</h3>
                <ResponsiveContainer width="100%" height={250}>
                  <PieChart>
                    <Pie
                      data={Object.entries(analyticsData.paymentMethodBreakdown).map(([name, value]) => ({ name, value }))}
                      cx="50%"
                      cy="50%"
                      innerRadius={60}
                      outerRadius={80}
                      paddingAngle={5}
                      dataKey="value"
                    >
                      {Object.keys(analyticsData.paymentMethodBreakdown).map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                      ))}
                    </Pie>
                    <Tooltip />
                  </PieChart>
                </ResponsiveContainer>
              </div>
            </div>

            {/* Low Stock Alert */}
            {lowStockItems.length > 0 && (
              <div className="card" style={{ padding: '20px' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '16px' }}>
                  <AlertTriangle size={20} color="#F59E0B" />
                  <h3 style={{ fontWeight: '600' }}>Low Stock Alert</h3>
                </div>
                <table>
                  <thead>
                    <tr>
                      <th>Product</th>
                      <th>Current Stock</th>
                      <th>Threshold</th>
                    </tr>
                  </thead>
                  <tbody>
                    {lowStockItems.slice(0, 5).map((item) => (
                      <tr key={item.id}>
                        <td>{item.productName}</td>
                        <td><span className="badge badge-warning">{item.quantity}</span></td>
                        <td>{item.lowStockThreshold}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </>
        )}

        {activeTab === 'orders' && (
          <div className="card">
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>Orders</h3>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>Order #</th>
                    <th>Date</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Payment</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.slice(0, 10).map((order) => (
                    <tr key={order.id}>
                      <td style={{ fontWeight: '600' }}>{order.orderNumber}</td>
                      <td>{order.createdAt}</td>
                      <td>${order.total?.toFixed(2) || '0.00'}</td>
                      <td><span className={`badge badge-${order.status === 'COMPLETED' ? 'success' : 'warning'}`}>{order.status}</span></td>
                      <td>{order.paymentMethod}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {activeTab === 'inventory' && (
          <div className="card">
            <div className="card-header">
              <h3>Inventory</h3>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>Product</th>
                    <th>SKU</th>
                    <th>Stock</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {inventory.map((item) => (
                    <tr key={item.id}>
                      <td>{item.productName}</td>
                      <td>{item.productBarcode}</td>
                      <td>{item.quantity}</td>
                      <td>
                        {item.isLowStock ? (
                          <span className="badge badge-warning">Low Stock</span>
                        ) : (
                          <span className="badge badge-success">In Stock</span>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}