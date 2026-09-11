package vn.edu.eaut.warehouse.model;

import java.time.LocalDateTime;

public class OrderStatusHistory {
    private Long id;
    private Long orderId;
    private String oldStatus;
    private String newStatus;
    private Long changedBy;
    private LocalDateTime changedAt;
    private String platform;
    private String note;

    public OrderStatusHistory() {}

    public OrderStatusHistory(Long id, Long orderId, String oldStatus, String newStatus, 
                               Long changedBy, LocalDateTime changedAt, String platform, String note) {
        this.id = id;
        this.orderId = orderId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedBy = changedBy;
        this.changedAt = changedAt;
        this.platform = platform;
        this.note = note;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getOldStatus() { return oldStatus; }
    public void setOldStatus(String oldStatus) { this.oldStatus = oldStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public Long getChangedBy() { return changedBy; }
    public void setChangedBy(Long changedBy) { this.changedBy = changedBy; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
