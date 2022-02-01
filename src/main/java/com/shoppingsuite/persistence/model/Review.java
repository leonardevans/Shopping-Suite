package com.shoppingsuite.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String detail;
    private Timestamp dateReviewed;

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
        final Review review = (Review) obj;
        if (!id.equals(review.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Review [id=")
                .append(id)
                .append(", detail=").append(detail)
                .append(", dateReviewed=").append(dateReviewed)
                .append(", user=").append(user)
                .append(", product=").append(product)
                .append("]");
        return builder.toString();
    }
}
