import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchStores } from '../store/slices/storeSlice'
import { fetchBranches } from '../store/slices/branchSlice'
import { fetchProducts, createProduct } from '../store/slices/productSlice'
import { fetchCategories } from '../store/slices/categorySlice'
import { fetchEmployees } from '../store/slices/employeeSlice'
import { fetchCustomers } from '../store/slices/customerSlice'
import { fetchOrders } from '../store/slices/orderSlice'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, BarChart, Bar } from 'recharts'
import { Store, Package, Users, ShoppingCart, TrendingUp, Settings, Plus, Building2, DollarSign, BarChart3, LogOut } from 'lucide-react'

export default function StoreAdmin() {
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)
  const { stores } = useSelector((state) => state.store)
  const { branches } = useSelector((state) => state.branch)
  const { products } = useSelector((state) => state.product)
  const { categories } = useSelector((state) => state.category)
  const { customers } = useSelector((state) => state.customer)
  const { orders } = useSelector((state) => state.order)

  const [activeTab, setActiveTab] = useState('dashboard')
  const [showProductModal, setShowProductModal] = useState(false)
  const [newProduct, setNewProduct] = useState({ name: '', price: '', categoryId: '' })

  useEffect(() => {
    dispatch(fetchStores())
    dispatch(fetchBranches())
    dispatch(fetchProducts(1))
    dispatch(fetchCategories(1))
    dispatch(fetchCustomers(1))
    dispatch(fetchOrders(1))
  }, [dispatch])

  const analyticsData = {
    totalRevenue: 12580.50,
    totalOrders: 156,
    avgOrderValue: 80.64,
    totalCustomers: 89,
    salesGrowth: 18.5,
    dailySales: [
      { date: 'Mon', sales: 1200, orders: 18 },
      { date: 'Tue', sales: 1450, orders: 22 },
      { date: 'Wed', sales: 1380, orders: 20 },
      { date: 'Thu', sales: 1680, orders: 26 },
      { date: 'Fri', sales: 2100, orders: 32 },
      { date: 'Sat', sales: 1850, orders: 28 },
      { date: 'Sun', sales: 1500, orders: 24 },
    ],
  }

  const stats = [
    { label: 'Total Revenue', value: `$${analyticsData.totalRevenue.toFixed(2)}`, icon: DollarSign, color: '#3B82F6', change: '+12.5%' },
    { label: 'Total Orders', value: analyticsData.totalOrders, icon: ShoppingCart, color: '#10B981', change: '+8.2%' },
    { label: 'Avg Order Value', value: `$${analyticsData.avgOrderValue.toFixed(2)}`, icon: TrendingUp, color: '#F59E0B', change: '+5.1%' },
    { label: 'Total Customers', value: analyticsData.totalCustomers, icon: Users, color: '#8B5CF6', change: '+15.3%' },
  ]

  const handleCreateProduct = () => {
    dispatch(createProduct({
      storeId: 1,
      name: newProduct.name,
      price: parseFloat(newProduct.price),
      categoryId: parseInt(newProduct.categoryId),
    }))
    setShowProductModal(false)
    setNewProduct({ name: '', price: '', categoryId: '' })
  }

  return (
    <div style={{ display: 'flex', minHeight: '100vh', background: '#F8FAFC' }}>
      {/* Sidebar */}
      <div style={{ width: '260px', background: '#0F172A', color: 'white', display: 'flex', flexDirection: 'column' }}>
        <div style={{ padding: '20px', borderBottom: '1px solid #1E293B' }}>
          <h2 style={{ fontSize: '20px', fontWeight: '700', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Store size={24} color="#3B82F6" />
            CloudPOS
          </h2>
          <p style={{ fontSize: '12px', color: '#64748B', marginTop: '4px' }}>Super Admin</p>
        </div>
        <nav style={{ flex: 1, padding: '16px' }}>
          {[
            { id: 'dashboard', label: 'Dashboard', icon: BarChart3 },
            { id: 'stores', label: 'Stores', icon: Store },
            { id: 'branches', label: 'Branches', icon: Building2 },
            { id: 'products', label: 'Products', icon: Package },
            { id: 'customers', label: 'Customers', icon: Users },
            { id: 'settings', label: 'Settings', icon: Settings },
          ].map((item) => (
            <button
              key={item.id}
              onClick={() => setActiveTab(item.id)}
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: '12px',
                width: '100%',
                padding: '12px 16px',
                marginBottom: '4px',
                borderRadius: '8px',
                textAlign: 'left',
                background: activeTab === item.id ? '#3B82F6' : 'transparent',
                color: activeTab === item.id ? 'white' : '#94A3B8',
                fontWeight: '500',
                transition: 'all 0.2s',
              }}
            >
              <item.icon size={18} />
              {item.label}
            </button>
          ))}
        </nav>
        <div style={{ padding: '16px', borderTop: '1px solid #1E293B' }}>
          <div style={{ marginBottom: '16px' }}>
            <p style={{ fontWeight: '600', fontSize: '14px' }}>{user?.firstName} {user?.lastName}</p>
            <p style={{ color: '#64748B', fontSize: '12px' }}>{user?.email}</p>
          </div>
          <button className="btn" style={{ width: '100%', background: '#1E293B', color: 'white' }} onClick={() => window.location.href = '/'}>
            <LogOut size={18} />
            Logout
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div style={{ flex: 1, padding: '24px' }}>
        {activeTab === 'dashboard' && (
          <>
            {/* Stats Grid */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '16px', marginBottom: '24px' }}>
              {stats.map((stat, idx) => (
                <div key={idx} className="card" style={{ padding: '20px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                    <div>
                      <p style={{ fontSize: '13px', color: '#64748B', marginBottom: '4px' }}>{stat.label}</p>
                      <p style={{ fontSize: '28px', fontWeight: '700' }}>{stat.value}</p>
                      <span style={{ fontSize: '12px', color: '#10B981' }}>{stat.change}</span>
                    </div>
                    <div style={{ padding: '12px', background: `${stat.color}15`, borderRadius: '12px' }}>
                      <stat.icon size={24} color={stat.color} />
                    </div>
                  </div>
                </div>
              ))}
            </div>

            {/* Charts */}
            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '16px', marginBottom: '24px' }}>
              <div className="card" style={{ padding: '24px' }}>
                <h3 style={{ marginBottom: '20px', fontWeight: '600', fontSize: '16px' }}>Weekly Revenue</h3>
                <ResponsiveContainer width="100%" height={300}>
                  <LineChart data={analyticsData.dailySales}>
                    <CartesianGrid strokeDasharray="3 3" stroke="#E2E8F0" />
                    <XAxis dataKey="date" stroke="#64748B" />
                    <YAxis stroke="#64748B" />
                    <Tooltip />
                    <Line type="monotone" dataKey="sales" stroke="#3B82F6" strokeWidth={3} dot={{ fill: '#3B82F6', r: 4 }} />
                  </LineChart>
                </ResponsiveContainer>
              </div>
              <div className="card" style={{ padding: '24px' }}>
                <h3 style={{ marginBottom: '20px', fontWeight: '600', fontSize: '16px' }}>Orders by Day</h3>
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart data={analyticsData.dailySales}>
                    <CartesianGrid strokeDasharray="3 3" stroke="#E2E8F0" />
                    <XAxis dataKey="date" stroke="#64748B" />
                    <YAxis stroke="#64748B" />
                    <Tooltip />
                    <Bar dataKey="orders" fill="#10B981" radius={[4, 4, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </div>

            {/* Quick Actions */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '16px' }}>
              <button className="btn btn-primary" style={{ padding: '20px', justifyContent: 'flex-start' }} onClick={() => setActiveTab('products')}>
                <Plus size={20} />
                Add Product
              </button>
              <button className="btn btn-outline" style={{ padding: '20px', justifyContent: 'flex-start' }} onClick={() => setActiveTab('branches')}>
                <Building2 size={20} />
                Add Branch
              </button>
              <button className="btn btn-outline" style={{ padding: '20px', justifyContent: 'flex-start' }} onClick={() => setActiveTab('customers')}>
                <Users size={20} />
                Add Customer
              </button>
              <button className="btn btn-outline" style={{ padding: '20px', justifyContent: 'flex-start' }} onClick={() => setActiveTab('settings')}>
                <Settings size={20} />
                Settings
              </button>
            </div>
          </>
        )}

        {activeTab === 'stores' && (
          <div className="card">
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>All Stores</h3>
              <button className="btn btn-primary">
                <Plus size={18} />
                Add Store
              </button>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Store Name</th>
                    <th>Address</th>
                    <th>Plan</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {stores.map((store) => (
                    <tr key={store.id}>
                      <td>{store.id}</td>
                      <td style={{ fontWeight: '600' }}>{store.name}</td>
                      <td>{store.address}</td>
                      <td><span className="badge badge-info">{store.subscriptionPlan}</span></td>
                      <td><span className="badge badge-success">Active</span></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {activeTab === 'branches' && (
          <div className="card">
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>All Branches</h3>
              <button className="btn btn-primary">
                <Plus size={18} />
                Add Branch
              </button>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Branch Name</th>
                    <th>Store</th>
                    <th>Manager</th>
                    <th>Contact</th>
                  </tr>
                </thead>
                <tbody>
                  {branches.map((branch) => (
                    <tr key={branch.id}>
                      <td>{branch.id}</td>
                      <td style={{ fontWeight: '600' }}>{branch.name}</td>
                      <td>{branch.storeName}</td>
                      <td>{branch.managerName || 'N/A'}</td>
                      <td>{branch.phone}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {activeTab === 'products' && (
          <div className="card">
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>All Products</h3>
              <button className="btn btn-primary" onClick={() => setShowProductModal(true)}>
                <Plus size={18} />
                Add Product
              </button>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Product Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {products.map((product) => (
                    <tr key={product.id}>
                      <td>{product.id}</td>
                      <td style={{ fontWeight: '600' }}>{product.name}</td>
                      <td>{product.categoryName || 'N/A'}</td>
                      <td>${product.price?.toFixed(2) || '0.00'}</td>
                      <td>
                        {product.active ? (
                          <span className="badge badge-success">Active</span>
                        ) : (
                          <span className="badge badge-danger">Inactive</span>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {activeTab === 'customers' && (
          <div className="card">
            <div className="card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>All Customers</h3>
              <button className="btn btn-primary">
                <Plus size={18} />
                Add Customer
              </button>
            </div>
            <div className="card-body">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Loyalty Points</th>
                  </tr>
                </thead>
                <tbody>
                  {customers.map((customer) => (
                    <tr key={customer.id}>
                      <td>{customer.id}</td>
                      <td style={{ fontWeight: '600' }}>{customer.firstName} {customer.lastName}</td>
                      <td>{customer.email}</td>
                      <td>{customer.phone}</td>
                      <td><span className="badge badge-info">{customer.loyaltyPoints || 0}</span></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>

      {/* Add Product Modal */}
      {showProductModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <div className="card" style={{ width: '400px' }}>
            <div style={{ padding: '20px', borderBottom: '1px solid #E2E8F0' }}>
              <h3>Add New Product</h3>
            </div>
            <div style={{ padding: '20px' }}>
              <div style={{ marginBottom: '16px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Product Name</label>
                <input
                  type="text"
                  className="input"
                  value={newProduct.name}
                  onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
                  placeholder="Enter product name"
                />
              </div>
              <div style={{ marginBottom: '16px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Price</label>
                <input
                  type="number"
                  className="input"
                  value={newProduct.price}
                  onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
                  placeholder="Enter price"
                />
              </div>
              <div style={{ marginBottom: '20px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Category</label>
                <select
                  className="input"
                  value={newProduct.categoryId}
                  onChange={(e) => setNewProduct({ ...newProduct, categoryId: e.target.value })}
                >
                  <option value="">Select category</option>
                  {categories.map((cat) => (
                    <option key={cat.id} value={cat.id}>{cat.name}</option>
                  ))}
                </select>
              </div>
              <div style={{ display: 'flex', gap: '12px' }}>
                <button className="btn btn-outline" style={{ flex: 1 }} onClick={() => setShowProductModal(false)}>Cancel</button>
                <button className="btn btn-primary" style={{ flex: 1 }} onClick={handleCreateProduct}>Add Product</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}