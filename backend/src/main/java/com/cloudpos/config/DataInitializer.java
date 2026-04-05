package com.cloudpos.config;

import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        // Create super admin user
        User superAdmin = User.builder()
                .email("admin@cloudpos.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .firstName("Super")
                .lastName("Admin")
                .role(User.UserRole.SUPER_ADMIN)
                .status(User.UserStatus.ACTIVE)
                .build();
        superAdmin = userRepository.save(superAdmin);

        // Create a store
        Store store = Store.builder()
                .name("CloudPOS Demo Store")
                .address("123 Main Street, City, State 12345")
                .phone("+1 (555) 123-4567")
                .email("store@cloudpos.com")
                .subscriptionPlan(Store.SubscriptionPlan.PRO)
                .build();
        store = storeRepository.save(store);

        // Create a branch
        Branch branch = Branch.builder()
                .store(store)
                .name("Main Branch")
                .address("123 Main Street, City, State 12345")
                .phone("+1 (555) 123-4567")
                .email("main@cloudpos.com")
                .manager(null)
                .build();
        branch = branchRepository.save(branch);

        // Create a store admin
        User storeAdmin = User.builder()
                .email("storeadmin@cloudpos.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .firstName("Store")
                .lastName("Admin")
                .role(User.UserRole.STORE_ADMIN)
                .status(User.UserStatus.ACTIVE)
                .build();
        storeAdmin = userRepository.save(storeAdmin);

        // Create a branch manager
        User branchManager = User.builder()
                .email("manager@cloudpos.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .firstName("Branch")
                .lastName("Manager")
                .role(User.UserRole.BRANCH_MANAGER)
                .status(User.UserStatus.ACTIVE)
                .build();
        branchManager = userRepository.save(branchManager);
        
        branch.setManager(branchManager);
        branchRepository.save(branch);

        // Create a cashier
        User cashier = User.builder()
                .email("cashier@cloudpos.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .firstName("John")
                .lastName("Cashier")
                .role(User.UserRole.CASHIER)
                .status(User.UserStatus.ACTIVE)
                .build();
        cashier = userRepository.save(cashier);

        // Create categories
        Category electronics = Category.builder()
                .store(store)
                .name("Electronics")
                .description("Electronic devices and accessories")
                .icon("laptop")
                .build();
        electronics = categoryRepository.save(electronics);

        Category beverages = Category.builder()
                .store(store)
                .name("Beverages")
                .description("Drinks and beverages")
                .icon("coffee")
                .build();
        beverages = categoryRepository.save(beverages);

        Category snacks = Category.builder()
                .store(store)
                .name("Snacks")
                .description("Snacks and confectionery")
                .icon("cookie")
                .build();
        snacks = categoryRepository.save(snacks);

        Category groceries = Category.builder()
                .store(store)
                .name("Groceries")
                .description("Daily groceries")
                .icon("shopping-cart")
                .build();
        groceries = categoryRepository.save(groceries);

        // Create products
        Product laptop = Product.builder()
                .store(store)
                .category(electronics)
                .name("Laptop Pro 15")
                .description("High-performance laptop")
                .barcode("LAP001")
                .price(BigDecimal.valueOf(999.99))
                .costPrice(BigDecimal.valueOf(750.00))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        laptop = productRepository.save(laptop);

        Product smartphone = Product.builder()
                .store(store)
                .category(electronics)
                .name("Smartphone X")
                .description("Latest smartphone")
                .barcode("PHN001")
                .price(BigDecimal.valueOf(699.99))
                .costPrice(BigDecimal.valueOf(500.00))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        smartphone = productRepository.save(smartphone);

        Product coffee = Product.builder()
                .store(store)
                .category(beverages)
                .name("Coffee")
                .description("Premium coffee")
                .barcode("COF001")
                .price(BigDecimal.valueOf(4.99))
                .costPrice(BigDecimal.valueOf(1.50))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        coffee = productRepository.save(coffee);

        Product soda = Product.builder()
                .store(store)
                .category(beverages)
                .name("Soda 500ml")
                .description("Refreshing soda")
                .barcode("SOD001")
                .price(BigDecimal.valueOf(2.49))
                .costPrice(BigDecimal.valueOf(0.75))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        soda = productRepository.save(soda);

        Product chips = Product.builder()
                .store(store)
                .category(snacks)
                .name("Chips")
                .description("Crispy chips")
                .barcode("CHP001")
                .price(BigDecimal.valueOf(3.99))
                .costPrice(BigDecimal.valueOf(1.00))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        chips = productRepository.save(chips);

        Product chocolate = Product.builder()
                .store(store)
                .category(snacks)
                .name("Chocolate Bar")
                .description("Delicious chocolate")
                .barcode("CHO001")
                .price(BigDecimal.valueOf(2.99))
                .costPrice(BigDecimal.valueOf(0.80))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        chocolate = productRepository.save(chocolate);

        Product rice = Product.builder()
                .store(store)
                .category(groceries)
                .name("Basmati Rice 5kg")
                .description("Premium basmati rice")
                .barcode("RIC001")
                .price(BigDecimal.valueOf(12.99))
                .costPrice(BigDecimal.valueOf(8.00))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        rice = productRepository.save(rice);

        Product oil = Product.builder()
                .store(store)
                .category(groceries)
                .name("Cooking Oil 1L")
                .description("Pure cooking oil")
                .barcode("OIL001")
                .price(BigDecimal.valueOf(8.99))
                .costPrice(BigDecimal.valueOf(5.00))
                .imageUrl("https://via.placeholder.com/150")
                .active(true)
                .build();
        oil = productRepository.save(oil);

        // Create inventory for branch
        inventoryRepository.save(Inventory.builder().product(laptop).branch(branch).quantity(10).lowStockThreshold(3).build());
        inventoryRepository.save(Inventory.builder().product(smartphone).branch(branch).quantity(15).lowStockThreshold(5).build());
        inventoryRepository.save(Inventory.builder().product(coffee).branch(branch).quantity(100).lowStockThreshold(20).build());
        inventoryRepository.save(Inventory.builder().product(soda).branch(branch).quantity(80).lowStockThreshold(15).build());
        inventoryRepository.save(Inventory.builder().product(chips).branch(branch).quantity(50).lowStockThreshold(10).build());
        inventoryRepository.save(Inventory.builder().product(chocolate).branch(branch).quantity(40).lowStockThreshold(10).build());
        inventoryRepository.save(Inventory.builder().product(rice).branch(branch).quantity(25).lowStockThreshold(5).build());
        inventoryRepository.save(Inventory.builder().product(oil).branch(branch).quantity(30).lowStockThreshold(8).build());

        // Create sample customers
        customerRepository.save(Customer.builder()
                .store(store)
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice@example.com")
                .phone("+1 (555) 111-2222")
                .loyaltyPoints(150)
                .build());

        customerRepository.save(Customer.builder()
                .store(store)
                .firstName("Bob")
                .lastName("Smith")
                .email("bob@example.com")
                .phone("+1 (555) 333-4444")
                .loyaltyPoints(75)
                .build());

        customerRepository.save(Customer.builder()
                .store(store)
                .firstName("Carol")
                .lastName("Williams")
                .email("carol@example.com")
                .phone("+1 (555) 555-6666")
                .loyaltyPoints(200)
                .build());

        System.out.println("=== Data Initialized Successfully ===");
        System.out.println("Default Login Credentials:");
        System.out.println("Super Admin: admin@cloudpos.com / admin123");
        System.out.println("Store Admin: storeadmin@cloudpos.com / admin123");
        System.out.println("Branch Manager: manager@cloudpos.com / admin123");
        System.out.println("Cashier: cashier@cloudpos.com / admin123");
    }
}