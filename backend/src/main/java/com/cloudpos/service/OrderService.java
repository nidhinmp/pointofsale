package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByBranch(Long branchId) {
        return orderRepository.findByBranchId(branchId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByStore(Long storeId) {
        return orderRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDTO(order);
    }

    public OrderDTO getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        User cashier = userRepository.findByEmail(request.getCashierEmail())
                .orElseThrow(() -> new RuntimeException("Cashier not found"));

        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            BigDecimal itemTotal = itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            subtotal = subtotal.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getUnitPrice())
                    .total(itemTotal)
                    .build();
            orderItems.add(orderItem);

            // Update inventory
            Inventory inventory = inventoryRepository.findByProductAndBranch(product, branch)
                    .orElse(null);
            if (inventory != null) {
                inventory.setQuantity(inventory.getQuantity() - itemRequest.getQuantity());
                inventoryRepository.save(inventory);
            }
        }

        // Calculate discount
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (request.getDiscountType() != null && request.getDiscountValue() != null) {
            if (request.getDiscountType().equals("PERCENTAGE")) {
                discountAmount = subtotal.multiply(request.getDiscountValue()).divide(BigDecimal.valueOf(100));
            } else {
                discountAmount = request.getDiscountValue();
            }
        }

        // Calculate tax (assume 10% tax rate)
        BigDecimal taxAmount = subtotal.subtract(discountAmount).multiply(BigDecimal.valueOf(0.10));

        // Calculate total
        BigDecimal total = subtotal.subtract(discountAmount).add(taxAmount);

        // Generate order number
        String orderNumber = "ORD-" + System.currentTimeMillis();

        Order order = Order.builder()
                .store(store)
                .branch(branch)
                .customer(customer)
                .cashier(cashier)
                .orderNumber(orderNumber)
                .subtotal(subtotal)
                .discountType(request.getDiscountType() != null ? Order.DiscountType.valueOf(request.getDiscountType()) : null)
                .discountValue(request.getDiscountValue())
                .discountAmount(discountAmount)
                .taxAmount(taxAmount)
                .total(total)
                .status(Order.OrderStatus.COMPLETED)
                .paymentMethod(request.getPaymentMethod() != null ? Order.PaymentMethod.valueOf(request.getPaymentMethod()) : Order.PaymentMethod.CASH)
                .notes(request.getNotes())
                .items(orderItems)
                .build();

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status));
        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    private OrderDTO mapToDTO(Order order) {
        List<OrderDTO.OrderItemDTO> items = order.getItems().stream()
                .map(item -> OrderDTO.OrderItemDTO.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .total(item.getTotal())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .storeId(order.getStore().getId())
                .branchId(order.getBranch().getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .cashierId(order.getCashier().getId())
                .orderNumber(order.getOrderNumber())
                .subtotal(order.getSubtotal())
                .discountType(order.getDiscountType() != null ? order.getDiscountType().name() : null)
                .discountValue(order.getDiscountValue())
                .discountAmount(order.getDiscountAmount())
                .taxAmount(order.getTaxAmount())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .paymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null)
                .notes(order.getNotes())
                .items(items)
                .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)
                .build();
    }
}