package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cash_flows")
public class CashFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor for JPA
    public CashFlow() {
    }

    // Constructor required by the test files
    public CashFlow(String type, String source, String label, Integer amount, String description) {
        this.type = type;
        this.source = source;
        this.label = label;
        this.amount = amount;
        this.description = description;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}