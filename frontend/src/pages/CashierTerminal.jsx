import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, searchProducts } from '../store/slices/productSlice'
import { fetchCategories } from '../store/slices/categorySlice'
import { fetchCustomers, setSelectedCustomer } from '../store/slices/customerSlice'
import { createOrder } from '../store/slices/orderSlice'
import { ShoppingCart, Search, Plus, Minus, Trash2, CreditCard, DollarSign, User, LogOut } from 'lucide-react'

export default function CashierTerminal() {
  const dispatch = useDispatch()
  const { products, loading: productsLoading } = useSelector((state) => state.product)
  const { categories } = useSelector((state) => state.category)
  const { customers } = useSelector((state) => state.customer)
  const { user, token } = useSelector((state) => state.auth)

  const [cart, setCart] = useState([])
  const [searchQuery, setSearchQuery] = useState('')
  const [selectedCategory, setSelectedCategory] = useState(null)
  const [selectedCustomer, setSelectedLocalCustomer] = useState(null)
  const [showCustomerModal, setShowCustomerModal] = useState(false)
  const [showPaymentModal, setShowPaymentModal] = useState(false)
  const [paymentMethod, setPaymentMethod] = useState('CASH')
  const [discountType, setDiscountType] = useState(null)
  const [discountValue, setDiscountValue] = useState(0)
  const [notes, setNotes] = useState('')

  useEffect(() => {
    // Hardcoded store ID for demo
    dispatch(fetchProducts(1))
    dispatch(fetchCategories(1))
    dispatch(fetchCustomers(1))
  }, [dispatch])

  const handleSearch = (query) => {
    setSearchQuery(query)
    if (query) {
      dispatch(searchProducts({ storeId: 1, query }))
    }
  }

  const addToCart = (product) => {
    setCart((prev) => {
      const existing = prev.find((item) => item.productId === product.id)
      if (existing) {
        return prev.map((item) =>
          item.productId === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      }
      return [
        ...prev,
        {
          productId: product.id,
          productName: product.name,
          price: product.price,
          quantity: 1,
        },
      ]
    })
  }

  const updateQuantity = (productId, delta) => {
    setCart((prev) =>
      prev.map((item) =>
        item.productId === productId
          ? { ...item, quantity: Math.max(1, item.quantity + delta) }
          : item
      )
    )
  }

  const removeFromCart = (productId) => {
    setCart((prev) => prev.filter((item) => item.productId !== productId))
  }

  const calculateTotals = () => {
    const subtotal = cart.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
    )
    let discount = 0
    if (discountType === 'PERCENTAGE') {
      discount = subtotal * (discountValue / 100)
    } else if (discountType === 'FIXED') {
      discount = discountValue
    }
    const tax = (subtotal - discount) * 0.1
    const total = subtotal - discount + tax
    return { subtotal, discount, tax, total }
  }

  const handleCheckout = async () => {
    const { total } = calculateTotals()
    const orderData = {
      storeId: 1,
      branchId: 1,
      customerId: selectedCustomer?.id || null,
      cashierEmail: user?.email,
      items: cart.map((item) => ({
        productId: item.productId,
        quantity: item.quantity,
        unitPrice: item.price,
      })),
      discountType,
      discountValue: discountType ? discountValue : null,
      paymentMethod,
      notes,
    }

    await dispatch(createOrder(orderData))
    setCart([])
    setSelectedLocalCustomer(null)
    setDiscountType(null)
    setDiscountValue(0)
    setNotes('')
    setShowPaymentModal(false)
    alert('Order placed successfully!')
  }

  const { subtotal, discount, tax, total } = calculateTotals()

  const filteredProducts = searchQuery
    ? products.filter((p) =>
        p.name.toLowerCase().includes(searchQuery.toLowerCase())
      )
    : selectedCategory
    ? products.filter((p) => p.categoryId === selectedCategory)
    : products

  return (
    <div style={{ display: 'flex', height: '100vh' }}>
      {/* Left Panel - Products */}
      <div style={{ flex: 1, display: 'flex', flexDirection: 'column', borderRight: '1px solid #E2E8F0' }}>
        {/* Header */}
        <div style={{ padding: '16px 20px', borderBottom: '1px solid #E2E8F0', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
            <h1 style={{ fontSize: '20px', fontWeight: '700' }}>CloudPOS</h1>
            <span className="badge badge-info">Cashier</span>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#64748B' }}>
              <User size={18} />
              <span>{user?.firstName} {user?.lastName}</span>
            </div>
            <button className="btn btn-outline" onClick={() => window.location.href = '/'}>
              <LogOut size={18} />
            </button>
          </div>
        </div>

        {/* Search and Categories */}
        <div style={{ padding: '16px', borderBottom: '1px solid #E2E8F0' }}>
          <div style={{ position: 'relative', marginBottom: '12px' }}>
            <Search size={18} style={{ position: 'absolute', left: '12px', top: '50%', transform: 'translateY(-50%)', color: '#64748B' }} />
            <input
              type="text"
              placeholder="Search products..."
              value={searchQuery}
              onChange={(e) => handleSearch(e.target.value)}
              className="input"
              style={{ paddingLeft: '40px' }}
            />
          </div>
          <div style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
            <button
              className={`btn ${!selectedCategory ? 'btn-primary' : 'btn-outline'}`}
              onClick={() => setSelectedCategory(null)}
              style={{ padding: '6px 12px', fontSize: '13px' }}
            >
              All
            </button>
            {categories.map((cat) => (
              <button
                key={cat.id}
                className={`btn ${selectedCategory === cat.id ? 'btn-primary' : 'btn-outline'}`}
                onClick={() => setSelectedCategory(cat.id)}
                style={{ padding: '6px 12px', fontSize: '13px' }}
              >
                {cat.name}
              </button>
            ))}
          </div>
        </div>

        {/* Products Grid */}
        <div style={{ flex: 1, overflow: 'auto', padding: '16px' }}>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(160px, 1fr))', gap: '12px' }}>
            {filteredProducts.map((product) => (
              <div
                key={product.id}
                onClick={() => addToCart(product)}
                className="card"
                style={{ cursor: 'pointer', padding: '12px', transition: 'transform 0.2s, box-shadow 0.2s' }}
                onMouseOver={(e) => { e.currentTarget.style.transform = 'translateY(-2px)', e.currentTarget.style.boxShadow = '0 4px 12px rgba(0,0,0,0.1)' }}
                onMouseOut={(e) => { e.currentTarget.style.transform = 'translateY(0)', e.currentTarget.style.boxShadow = '' }}
              >
                <div style={{ height: '80px', background: '#F1F5F9', borderRadius: '8px', marginBottom: '8px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                  <ShoppingCart size={24} color="#64748B" />
                </div>
                <h3 style={{ fontSize: '14px', fontWeight: '600', marginBottom: '4px' }}>{product.name}</h3>
                <p style={{ fontSize: '18px', fontWeight: '700', color: '#3B82F6' }}>${product.price.toFixed(2)}</p>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Right Panel - Cart */}
      <div style={{ width: '400px', display: 'flex', flexDirection: 'column', background: '#F8FAFC' }}>
        {/* Cart Header */}
        <div style={{ padding: '16px 20px', borderBottom: '1px solid #E2E8F0', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
          <h2 style={{ fontSize: '18px', fontWeight: '700' }}>Current Order</h2>
          <button className="btn btn-outline" onClick={() => setCart([])} style={{ fontSize: '12px' }}>
            Clear
          </button>
        </div>

        {/* Cart Items */}
        <div style={{ flex: 1, overflow: 'auto', padding: '16px' }}>
          {cart.length === 0 ? (
            <div style={{ textAlign: 'center', color: '#64748B', padding: '40px 20px' }}>
              <ShoppingCart size={48} style={{ marginBottom: '16px', opacity: 0.5 }} />
              <p>No items in cart</p>
            </div>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
              {cart.map((item) => (
                <div key={item.productId} className="card" style={{ padding: '12px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '8px' }}>
                    <div>
                      <h4 style={{ fontSize: '14px', fontWeight: '600' }}>{item.productName}</h4>
                      <p style={{ fontSize: '12px', color: '#64748B' }}>${item.price.toFixed(2)} each</p>
                    </div>
                    <button onClick={() => removeFromCart(item.productId)} style={{ background: 'none', color: '#EF4444' }}>
                      <Trash2 size={16} />
                    </button>
                  </div>
                  <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                      <button className="btn btn-outline" style={{ padding: '4px 8px' }} onClick={() => updateQuantity(item.productId, -1)}>
                        <Minus size={14} />
                      </button>
                      <span style={{ fontWeight: '600', minWidth: '24px', textAlign: 'center' }}>{item.quantity}</span>
                      <button className="btn btn-outline" style={{ padding: '4px 8px' }} onClick={() => updateQuantity(item.productId, 1)}>
                        <Plus size={14} />
                      </button>
                    </div>
                    <span style={{ fontWeight: '700' }}>${(item.price * item.quantity).toFixed(2)}</span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Customer */}
        <div style={{ padding: '12px 16px', borderTop: '1px solid #E2E8F0' }}>
          <button className="btn btn-outline" style={{ width: '100%', justifyContent: 'flex-start' }} onClick={() => setShowCustomerModal(true)}>
            <User size={18} />
            {selectedCustomer ? `${selectedCustomer.firstName} ${selectedCustomer.lastName}` : 'Add Customer'}
          </button>
        </div>

        {/* Totals */}
        <div style={{ padding: '16px', borderTop: '1px solid #E2E8F0', background: 'white' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ color: '#64748B' }}>Subtotal</span>
            <span>${subtotal.toFixed(2)}</span>
          </div>
          {discount > 0 && (
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px', color: '#10B981' }}>
              <span>Discount</span>
              <span>-${discount.toFixed(2)}</span>
            </div>
          )}
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ color: '#64748B' }}>Tax (10%)</span>
            <span>${tax.toFixed(2)}</span>
          </div>
          <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '20px', fontWeight: '700', paddingTop: '12px', borderTop: '1px solid #E2E8F0' }}>
            <span>Total</span>
            <span style={{ color: '#3B82F6' }}>${total.toFixed(2)}</span>
          </div>
        </div>

        {/* Checkout Button */}
        <div style={{ padding: '16px', borderTop: '1px solid #E2E8F0' }}>
          <button
            className="btn btn-primary"
            style={{ width: '100%', padding: '16px', fontSize: '16px' }}
            onClick={() => setShowPaymentModal(true)}
            disabled={cart.length === 0}
          >
            <CreditCard size={20} />
            Checkout
          </button>
        </div>
      </div>

      {/* Customer Modal */}
      {showCustomerModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <div className="card" style={{ width: '400px', maxHeight: '80vh', overflow: 'auto' }}>
            <div style={{ padding: '16px', borderBottom: '1px solid #E2E8F0', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>Select Customer</h3>
              <button onClick={() => setShowCustomerModal(false)} style={{ background: 'none', fontSize: '20px' }}>×</button>
            </div>
            <div style={{ padding: '16px' }}>
              <input type="text" placeholder="Search customers..." className="input" style={{ marginBottom: '16px' }} />
              <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                {customers.map((customer) => (
                  <div
                    key={customer.id}
                    onClick={() => { setSelectedLocalCustomer(customer); setShowCustomerModal(false) }}
                    style={{ padding: '12px', border: '1px solid #E2E8F0', borderRadius: '8px', cursor: 'pointer', display: 'flex', justifyContent: 'space-between' }}
                  >
                    <div>
                      <p style={{ fontWeight: '600' }}>{customer.firstName} {customer.lastName}</p>
                      <p style={{ fontSize: '12px', color: '#64748B' }}>{customer.email}</p>
                    </div>
                    <span className="badge badge-info">{customer.looyaltyPoints || 0} pts</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Payment Modal */}
      {showPaymentModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <div className="card" style={{ width: '400px' }}>
            <div style={{ padding: '16px', borderBottom: '1px solid #E2E8F0' }}>
              <h3>Payment</h3>
            </div>
            <div style={{ padding: '16px' }}>
              <div style={{ marginBottom: '16px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Payment Method</label>
                <div style={{ display: 'flex', gap: '8px' }}>
                  <button className={`btn ${paymentMethod === 'CASH' ? 'btn-primary' : 'btn-outline'}`} onClick={() => setPaymentMethod('CASH')} style={{ flex: 1 }}>
                    <DollarSign size={18} />
                    Cash
                  </button>
                  <button className={`btn ${paymentMethod === 'CARD' ? 'btn-primary' : 'btn-outline'}`} onClick={() => setPaymentMethod('CARD')} style={{ flex: 1 }}>
                    <CreditCard size={18} />
                    Card
                  </button>
                </div>
              </div>
              <div style={{ marginBottom: '16px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Discount</label>
                <select className="input" value={discountType || ''} onChange={(e) => setDiscountType(e.target.value || null)}>
                  <option value="">No Discount</option>
                  <option value="PERCENTAGE">Percentage</option>
                  <option value="FIXED">Fixed Amount</option>
                </select>
                {discountType && (
                  <input type="number" className="input" style={{ marginTop: '8px' }} value={discountValue} onChange={(e) => setDiscountValue(parseFloat(e.target.value) || 0)} placeholder="Value" />
                )}
              </div>
              <div style={{ marginBottom: '16px' }}>
                <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Notes</label>
                <textarea className="input" value={notes} onChange={(e) => setNotes(e.target.value)} rows={3} placeholder="Order notes..." />
              </div>
              <div style={{ padding: '16px', background: '#F8FAFC', borderRadius: '8px', marginBottom: '16px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '18px', fontWeight: '700' }}>
                  <span>Total</span>
                  <span>${total.toFixed(2)}</span>
                </div>
              </div>
              <button className="btn btn-success" style={{ width: '100%', padding: '14px' }} onClick={handleCheckout}>
                Complete Payment
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}