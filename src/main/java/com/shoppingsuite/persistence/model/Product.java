package com.shoppingsuite.persistence.model;

import com.shoppingsuite.web.dto.ProductDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private double price;
    private double stock;
    private String imageUrl;
    private boolean published;

    public Product(ProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.stock = productDto.getStock();
        this.imageUrl = productDto.getImageUrl();
        this.published = productDto.isPublished();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Cart> carts = new HashSet();

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet();

    @OneToMany(mappedBy = "product")
    private Set<Deal> deals = new HashSet();

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
