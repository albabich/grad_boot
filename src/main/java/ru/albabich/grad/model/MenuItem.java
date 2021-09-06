package ru.albabich.grad.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "restaurant")
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"}, name = "menu_item_unique_restaurant_id_date_name_idx")})
public class MenuItem extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference(value = "menu")
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1)
    private int price;

    public MenuItem(MenuItem menuItem1) {
        this(menuItem1.id, menuItem1.date, menuItem1.name, menuItem1.price);
    }

    public MenuItem(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public MenuItem(Integer id, LocalDate date, String name, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }
}
