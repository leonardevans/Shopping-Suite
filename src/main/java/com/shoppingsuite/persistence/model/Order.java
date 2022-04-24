package com.shoppingsuite.persistence.model;

import com.shoppingsuite.persistence.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean paid = false;

    @Column(columnDefinition = "text")
    private String payment_details;

    private double amount;
    private String currency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private java.util.Date orderDate;

    public Order(Cart cart, double amount, String currency) {
        this.cart = cart;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order order = (Order) obj;
        if (!id.equals(order.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Order [id=")
                .append(id)
                .append(", cart=").append(cart)
                .append(", cart=").append(cart)
                .append(", orderStatus=").append(orderStatus)
                .append(", orderDate=").append(orderDate)
                .append("]");
        return builder.toString();
    }
}
