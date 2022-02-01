package com.shoppingsuite.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int value;

    @Column(nullable = false, updatable = false, insertable = false)
    private Timestamp dateRated;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

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
        final Rating rating = (Rating) obj;
        if (!id.equals(rating.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Review [id=")
                .append(id)
                .append(", value=").append(value)
                .append(", dateRated=").append(dateRated)
                .append(", user=").append(user)
                .append(", product=").append(product)
                .append("]");
        return builder.toString();
    }
}
