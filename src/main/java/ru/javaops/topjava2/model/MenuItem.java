package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static java.time.LocalDate.now;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = "restaurant")
@Table(name = "menu_item")
public class MenuItem extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1)
    private int price;

    public MenuItem(MenuItem menuItem1) {
        this(menuItem1.id, menuItem1.date, menuItem1.name, menuItem1.price);
    }

    public MenuItem(Integer id, String name, int price) {
        this(id, now(), name, price);
    }

    public MenuItem(Integer id, LocalDate date, String name, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }
}
