package vn.edu.eaut.warehouse.model;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime createdAt;
    private Double totalAmount;
    private OrderStatus status;
    private String note;
    private Integer version;

    public Order() {}

    public Order(Long id, Long customerId, String customerName, LocalDateTime createdAt, 
                  Double totalAmount, OrderStatus status, String note, Integer version) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.note = note;
        this.version = version;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
