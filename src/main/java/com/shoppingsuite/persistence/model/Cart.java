package com.shoppingsuite.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingsuite.persistence.enums.OrderStatus;
import com.shoppingsuite.persistence.joins.CartProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<CartProduct> cartProducts = new HashSet<>();

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;

    private boolean ordered = false;
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
        if (id != cart.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Cart [id=")
                .append(id)
                .append(", cartProducts=").append(cartProducts)
                .append(", user=").append(user)
                .append(", ordered=").append(ordered)
                .append(", total=").append(total)
                .append("]");
        return builder.toString();
    }
}
