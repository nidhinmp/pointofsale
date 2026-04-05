# SaaS Point of Sale (POS) System Specification

## 1. Project Overview

**Project Name:** CloudPOS - Enterprise SaaS Point of Sale System  
**Project Type:** Full-stack Multi-tenant SaaS Web Application  
**Core Functionality:** A comprehensive point of sale system with multiple dashboards for cashiers, branch managers, store admins, and super administrators  
**Target Users:** Retail businesses, restaurants, and multi-location enterprises requiring centralized sales management

---

## 2. Technology Stack

### Backend
- **Framework:** Spring Boot 3.x (Java 17)
- **Database:** MySQL 8.x
- **Security:** Spring Security with JWT Authentication
- **ORM:** Spring Data JPA / Hibernate
- **API:** RESTful APIs (14+ modules)
- **Payment Gateways:** Razorpay, Stripe integration ready

### Frontend
- **Framework:** React 18 with Vite
- **State Management:** Redux Toolkit (16 reducers)
- **UI Components:** Custom components inspired by shadcn/ui
- **HTTP Client:** Axios
- **Routing:** React Router v6

---

## 3. User Roles & Permissions

| Role | Permissions |
|------|-------------|
| SUPER_ADMIN | Full system access, manage all stores, subscription plans |
| STORE_ADMIN | Manage stores, branches, employees, analytics |
| BRANCH_MANAGER | Branch-specific operations, inventory, employees |
| CASHIER | Process sales, refunds, view own shift reports |

---

## 4. UI/UX Specification

### 4.1 Design System

**Color Palette:**
- Primary: `#0F172A` (Slate 900 - dark navy)
- Secondary: `#3B82F6` (Blue 500 - vibrant blue)
- Accent: `#10B981` (Emerald 500 - success green)
- Warning: `#F59E0B` (Amber 500)
- Danger: `#EF4444` (Red 500)
- Background: `#F8FAFC` (Slate 50)
- Card Background: `#FFFFFF`
- Text Primary: `#1E293B` (Slate 800)
- Text Secondary: `#64748B` (Slate 500)
- Border: `#E2E8F0` (Slate 200)

**Typography:**
- Headings: Inter, system-ui, sans-serif
- Body: Inter, system-ui, sans-serif
- Font Sizes: 
  - H1: 32px / font-weight: 700
  - H2: 24px / font-weight: 600
  - H3: 20px / font-weight: 600
  - Body: 14px / font-weight: 400
  - Small: 12px / font-weight: 400

**Spacing System:**
- Base unit: 4px
- Margins/Padding: 4px, 8px, 12px, 16px, 24px, 32px, 48px

**Border Radius:**
- Small: 4px
- Medium: 8px
- Large: 12px
- XL: 16px

**Shadows:**
- Small: `0 1px 2px rgba(0, 0, 0, 0.05)`
- Medium: `0 4px 6px -1px rgba(0, 0, 0, 0.1)`
- Large: `0 10px 15px -3px rgba(0, 0, 0, 0.1)`

### 4.2 Responsive Breakpoints
- Mobile: < 640px
- Tablet: 640px - 1024px
- Desktop: > 1024px

---

## 5. Application Interfaces

### 5.1 Authentication Pages

**Login Page:**
- Centered card with logo
- Email input field
- Password input field with show/hide toggle
- "Remember me" checkbox
- Login button (primary blue)
- "Forgot password?" link
- Background: subtle gradient or solid color

### 5.2 Cashier Terminal (Customer-facing checkout)

**Layout:**
- Left panel (65%): Product catalog with search bar, categories, products grid
- Right panel (35%): Cart summary, customer info, payment buttons

**Components:**
- Search bar with barcode scanning hint (F1 hotkey)
- Category tabs/pills
- Product cards (image, name, price, quick add button)
- Cart items list (product, quantity controls, line total, remove)
- Customer selector dropdown
- Discount input (percentage or fixed)
- Order notes textarea
- Subtotal, discount, tax, total display
- Payment method selector (Cash, Card, UPI, Wallet)
- "Complete Sale" button (large, prominent)
- "Hold Order" button
- "New Order" button

**Features:**
- Barcode search with F1 shortcut
- Quick quantity adjustment (+/-)
- Real-time total calculation
- Multiple payment method selection
- Receipt generation ready
- Shift indicator in header

### 5.3 Branch Manager Dashboard

**Layout:**
- Collapsible sidebar navigation
- Top header with branch name, user menu, notifications
- Main content area with cards and tables

**Pages:**
1. **Dashboard Home**
   - Today's sales summary card
   - Orders count (completed, pending, refunded)
   - Low stock alerts list
   - Top selling products chart
   - Recent orders list

2. **Order Management**
   - Orders table with filters (date range, status, payment method)
   - Search by order ID or customer
   - Order details modal
   - Refund action

3. **Inventory**
   - Products table with stock levels
   - Category filter
   - Low stock threshold alerts
   - Quick stock adjustment

4. **Employees**
   - Employee list with status (active/inactive)
   - Add new employee form
   - Edit employee modal
   - Role assignment

5. **Analytics**
   - Sales trend chart (last 7 days)
   - Payment method breakdown pie chart
   - Branch performance metrics
   - Top products table

6. **Settings**
   - Branch information
   - Tax configuration
   - Receipt settings

### 5.4 Store Admin Panel

**Layout:**
- Fixed sidebar with store/branches tree
- Top header with store name, super admin controls, user menu

**Pages:**
1. **Dashboard**
   - Enterprise-wide analytics
   - All branch performance comparison
   - Total sales, orders, customers metrics
   - Growth indicators

2. **Multi-location Management**
   - Store list with branches count
   - Add/edit store form
   - Branch management within stores
   - Branch assignment to store admins

3. **Product Catalog**
   - Global product catalog
   - Category management
   - Product inventory across branches
   - Bulk operations

4. **Employee Management**
   - All employees across locations
   - Role management
   - Performance overview

5. **Customer Loyalty**
   - Customer database
   - Loyalty points management
   - Purchase history

6. **Subscription Plans**
   - Plan configuration (Basic, Pro, Advance)
   - Feature toggles
   - Pricing management

7. **Analytics**
   - Cross-branch analytics
   - Sales trends
   - Growth tracking
   - KPI dashboard

---

## 6. API Specification

### 6.1 Authentication API
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Refresh token
- `POST /api/auth/logout` - Logout

### 6.2 User API
- `GET /api/users` - List all users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### 6.3 Store API
- `GET /api/stores` - List stores
- `POST /api/stores` - Create store
- `GET /api/stores/{id}` - Get store
- `PUT /api/stores/{id}` - Update store
- `DELETE /api/stores/{id}` - Delete store

### 6.4 Branch API
- `GET /api/branches` - List branches
- `POST /api/branches` - Create branch
- `GET /api/branches/{id}` - Get branch
- `PUT /api/branches/{id}` - Update branch
- `DELETE /api/branches/{id}` - Delete branch

### 6.5 Product API
- `GET /api/products` - List products
- `POST /api/products` - Create product
- `GET /api/products/{id}` - Get product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search` - Search products
- `PUT /api/products/{id}/stock` - Update stock

### 6.6 Category API
- `GET /api/categories` - List categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### 6.7 Customer API
- `GET /api/customers` - List customers
- `POST /api/customers` - Create customer
- `GET /api/customers/{id}` - Get customer
- `PUT /api/customers/{id}` - Update customer
- `GET /api/customers/{id}/orders` - Get customer orders

### 6.8 Order API
- `POST /api/orders` - Create order
- `GET /api/orders` - List orders with filters
- `GET /api/orders/{id}` - Get order details
- `PUT /api/orders/{id}/status` - Update order status

### 6.9 Refund API
- `POST /api/refunds` - Create refund
- `GET /api/refunds` - List refunds
- `GET /api/refunds/{id}` - Get refund details

### 6.10 Employee API
- `GET /api/employees` - List employees
- `POST /api/employees` - Create employee
- `PUT /api/employees/{id}` - Update employee
- `PUT /api/employees/{id}/status` - Toggle active status

### 6.11 Shift Report API
- `POST /api/shifts` - Start shift
- `GET /api/shifts/current` - Get current shift
- `POST /api/shifts/end` - End shift
- `GET /api/shifts/{id}/report` - Get shift report

### 6.12 Analytics API
- `GET /api/analytics/branch/{id}` - Branch analytics
- `GET /api/analytics/store/{id}` - Store analytics
- `GET /api/analytics/daily-sales` - Daily sales
- `GET /api/analytics/top-products` - Top products

---

## 7. Database Schema

### Core Entities

```
users
├── id (PK)
├── email (unique)
├── password_hash
├── first_name
├── last_name
├── role (ENUM: SUPER_ADMIN, STORE_ADMIN, BRANCH_MANAGER, CASHIER)
├── status (ENUM: ACTIVE, INACTIVE)
├── created_at
├── updated_at

stores
├── id (PK)
├── name
├── address
├── phone
├── email
├── subscription_plan
├── subscription_expiry
├── created_at
├── updated_at

branches
├── id (PK)
├── store_id (FK)
├── name
├── address
├── phone
├── email
├── manager_id (FK -> users)
├── created_at
├── updated_at

categories
├── id (PK)
├── store_id (FK)
├── name
├── description
├── created_at

products
├── id (PK)
├── store_id (FK)
├── category_id (FK)
├── name
├── description
├── barcode
├── price
├── cost_price
├── image_url
├── created_at
├── updated_at

inventory
├── id (PK)
├── product_id (FK)
├── branch_id (FK)
├── quantity
├── low_stock_threshold
├── updated_at

customers
├── id (PK)
├── store_id (FK)
├── first_name
├── last_name
├── email
├── phone
├── loyalty_points
├── created_at
├── updated_at

orders
├── id (PK)
├── store_id (FK)
├── branch_id (FK)
├── customer_id (FK)
├── cashier_id (FK -> users)
├── order_number (unique)
├── subtotal
├── discount_type (PERCENTAGE, FIXED)
├── discount_value
├── discount_amount
├── tax_amount
├── total
├── status (PENDING, COMPLETED, CANCELLED, REFUNDED)
├── payment_method
├── notes
├── created_at
├── updated_at

order_items
├── id (PK)
├── order_id (FK)
├── product_id (FK)
├── quantity
├── unit_price
├── total

refunds
├── id (PK)
├── order_id (FK)
├── amount
├── reason
├── status (PENDING, APPROVED, REJECTED)
├── created_by (FK -> users)
├── created_at

employees
├── id (PK)
├── user_id (FK)
├── branch_id (FK)
├── store_id (FK)
├── employee_id (internal)
├── hire_date
├── status
├── created_at

shifts
├── id (PK)
├── branch_id (FK)
├── user_id (FK)
├── start_time
├── end_time
├── opening_cash
├── closing_cash
├── expected_cash
├── status (OPEN, CLOSED)
├── created_at

subscription_plans
├── id (PK)
├── name
├── price
├── max_branches
├── max_users
├── max_products
├── features (JSON)
├── created_at
```

---

## 8. Redux State Structure

```javascript
{
  auth: { user, token, isAuthenticated, loading, error },
  user: { currentUser, users, loading, error },
  customer: { customers, selectedCustomer, loading, error },
  order: { orders, currentOrder, loading, error },
  refund: { refunds, loading, error },
  shift: { currentShift, shiftReport, loading, error },
  branch: { branches, currentBranch, loading, error },
  store: { stores, currentStore, loading, error },
  product: { products, selectedProduct, loading, error },
  inventory: { inventory, loading, error },
  employee: { employees, loading, error },
  category: { categories, loading, error },
  branchAnalytics: { data, loading, error },
  storeAnalytics: { data, loading, error },
  subscription: { plans, currentPlan, loading, error },
  ui: { sidebarOpen, notifications }
}
```

---

## 9. Acceptance Criteria

### Authentication
- [ ] Users can login with email/password
- [ ] JWT tokens are issued and validated
- [ ] Role-based access is enforced
- [ ] Unauthorized access returns 403

### Cashier Terminal
- [ ] Products display in grid with images
- [ ] Search filters products in real-time
- [ ] Adding to cart updates total immediately
- [ ] Quantity controls work correctly
- [ ] Discount applies correctly to total
- [ ] Payment completion creates order
- [ ] Receipt data is generated

### Branch Dashboard
- [ ] Dashboard shows today's metrics
- [ ] Orders can be filtered and searched
- [ ] Inventory shows stock levels
- [ ] Low stock alerts display correctly
- [ ] Employees can be managed
- [ ] Analytics charts render correctly

### Store Admin Panel
- [ ] Multi-branch overview displays
- [ ] Store and branch CRUD operations work
- [ ] Global product catalog is manageable
- [ ] Cross-branch analytics display
- [ ] Subscription plans are configurable

### API
- [ ] All endpoints return proper responses
- [ ] Error handling returns appropriate codes
- [ ] Data validation is enforced
- [ ] Pagination works correctly

---

## 10. Project Structure

```
pointofsale/
├── backend/                 # Spring Boot application
│   ├── src/main/java/
│   │   └── com/cloudpos/
│   │       ├── config/      # Security, CORS, JWT config
│   │       ├── controller/ # REST controllers
│   │       ├── service/     # Business logic
│   │       ├── repository/ # Data access
│   │       ├── entity/      # JPA entities
│   │       ├── dto/         # Data transfer objects
│   │       └── security/    # JWT, Auth filters
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── frontend/                # React application
│   ├── src/
│   │   ├── components/      # Reusable UI components
│   │   ├── pages/           # Page components
│   │   ├── store/           # Redux store & slices
│   │   ├── services/        # API calls
│   │   ├── hooks/           # Custom hooks
│   │   ├── utils/           # Helper functions
│   │   ├── styles/          # Global styles
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── package.json
│   └── vite.config.js
│
├── SPEC.md
└── README.md
```