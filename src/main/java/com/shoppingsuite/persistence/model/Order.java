package com.shoppingsuite.persistence.model;

import com.shoppingsuite.persistence.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    private OrderStatus orderStatus;


    @Column(nullable = false, updatable = false, insertable = false)
    private Timestamp orderDate;

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