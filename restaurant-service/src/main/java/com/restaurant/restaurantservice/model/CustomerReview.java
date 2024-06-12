package com.restaurant.restaurantservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customerReviews", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "restaurantId"})})
public class CustomerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "comment")
    private String comment;

    @Column(name = "service_rating", nullable = false)
    private Integer serviceRating;

    @Column(name = "taste_rating", nullable = false)
    private Integer tasteRating;

    @Column(name = "price_rating", nullable = false)
    private Integer priceRating;

    @Transient
    private Double averageRating;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateAverageRating() {
        this.averageRating = (serviceRating + tasteRating + priceRating) / 3.0;
    }
}
