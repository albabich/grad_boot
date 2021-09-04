package ru.albabich.grad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"user", "restaurant"})
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "vote_unique_date_user_id_idx")})
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @NotNull
    private User user;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference(value = "rest")
    @NotNull
    private Restaurant restaurant;

    public Vote(Integer id, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
    }

    public Vote(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
    }
}
