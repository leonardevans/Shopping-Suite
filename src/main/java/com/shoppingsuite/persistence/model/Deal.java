package com.shoppingsuite.persistence.model;

import com.shoppingsuite.web.dto.DealDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private double dealPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private boolean published;

    public Deal(DealDto dealDto) {
        this.id = dealDto.getId();
        this.product = dealDto.getProduct();
        this.dealPrice = dealDto.getDealPrice();
        this.startDate = dealDto.getStartDate();
        this.endDate = dealDto.getEndDate();
        this.published = dealDto.isPublished();
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
        final Deal deal = (Deal) obj;
        if (!id.equals(deal.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Deal [id=")
                .append(id)
                .append(", product=").append(product)
                .append(", startDate=").append(startDate)
                .append(", endDate=").append(endDate)
                .append(", dealPrice=").append(dealPrice)
                .append("]");
        return builder.toString();
    }
}
