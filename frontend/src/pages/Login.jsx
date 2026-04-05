import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { login } from '../store/slices/authSlice'
import { ShoppingCart, Lock, Mail } from 'lucide-react'

export default function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const dispatch = useDispatch()
  const { loading, error } = useSelector((state) => state.auth)

  const handleSubmit = (e) => {
    e.preventDefault()
    dispatch(login({ email, password }))
  }

  return (
    <div style={{ 
      minHeight: '100vh', 
      display: 'flex', 
      alignItems: 'center', 
      justifyContent: 'center',
      background: 'linear-gradient(135deg, #0F172A 0%, #1E293B 100%)' 
    }}>
      <div className="card" style={{ width: '100%', maxWidth: '400px', padding: '32px' }}>
        <div style={{ textAlign: 'center', marginBottom: '32px' }}>
          <div style={{ 
            display: 'inline-flex', 
            alignItems: 'center', 
            justifyContent: 'center',
            width: '64px', 
            height: '64px', 
            background: '#3B82F6', 
            borderRadius: '16px',
            marginBottom: '16px'
          }}>
            <ShoppingCart size={32} color="white" />
          </div>
          <h1 style={{ fontSize: '24px', fontWeight: '700', color: '#1E293B', marginBottom: '8px' }}>
            CloudPOS
          </h1>
          <p style={{ color: '#64748B' }}>Enterprise Point of Sale System</p>
        </div>

        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '20px' }}>
            <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500', color: '#1E293B' }}>
              Email
            </label>
            <div style={{ position: 'relative' }}>
              <Mail size={18} style={{ position: 'absolute', left: '12px', top: '50%', transform: 'translateY(-50%)', color: '#64748B' }} />
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="input"
                style={{ paddingLeft: '40px' }}
                placeholder="Enter your email"
                required
              />
            </div>
          </div>

          <div style={{ marginBottom: '24px' }}>
            <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500', color: '#1E293B' }}>
              Password
            </label>
            <div style={{ position: 'relative' }}>
              <Lock size={18} style={{ position: 'absolute', left: '12px', top: '50%', transform: 'translateY(-50%)', color: '#64748B' }} />
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="input"
                style={{ paddingLeft: '40px' }}
                placeholder="Enter your password"
                required
              />
            </div>
          </div>

          {error && (
            <div style={{ 
              padding: '12px', 
              background: '#FEE2E2', 
              color: '#991B1B', 
              borderRadius: '8px', 
              marginBottom: '16px',
              fontSize: '14px'
            }}>
              {error}
            </div>
          )}

          <button
            type="submit"
            className="btn btn-primary"
            style={{ width: '100%', padding: '12px' }}
            disabled={loading}
          >
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>

        <div style={{ marginTop: '24px', textAlign: 'center', fontSize: '13px', color: '#64748B' }}>
          <p>Demo Credentials:</p>
          <p>admin@cloudpos.com / admin123</p>
        </div>
      </div>
    </div>
  )
}