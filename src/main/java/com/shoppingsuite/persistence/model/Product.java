package com.shoppingsuite.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private double price;
    private double stock;
    private boolean published;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();

    @ManyToOne
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Deal> deals = new ArrayList<>();

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
        final Product product = (Product) obj;
        if (!id.equals(product.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Product [id=")
                .append(id)
                .append(", name=").append(name)
                .append(", price=").append(price)
                .append(", stock=").append(stock)
                .append(", published=").append(published)
                .append(", productCategory=").append(productCategory)
                .append(", reviews=").append(reviews)
                .append(", ratings=").append(ratings)
                .append(", deals=").append(deals)
                .append("]");
        return builder.toString();
    }
}
