package com.shoppingsuite.persistence.model;

import com.shoppingsuite.persistence.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;

    private boolean ordered = false;
    private OrderStatus orderStatus;
    private double total;

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
        final Cart cart = (Cart) obj;
        if (!id.equals(cart.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Cart [id=")
                .append(id)
                .append("]");
        return builder.toString();
    }
}
