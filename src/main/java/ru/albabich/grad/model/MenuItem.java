package ru.albabich.grad.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "available", "name"}, name = "menu_item_unique_restaurant_id_available_name_idx")})
public class MenuItem extends NamedEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "available", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate available;

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    private int price;

    public MenuItem(MenuItem menuItem) {
        this(menuItem.id, menuItem.available, menuItem.name, menuItem.price);
    }

    public MenuItem(Integer id, LocalDate available, String name, int price) {
        super(id, name);
        this.available = available;
        this.price = price;
    }
}
