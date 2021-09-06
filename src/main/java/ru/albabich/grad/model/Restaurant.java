package ru.albabich.grad.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurant_unique_name_idx")})
@ToString(callSuper = true, exclude = {"menuItems", "votes"})
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference(value = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MenuItem> menuItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference(value = "rest")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Restaurant rest1) {
        this(rest1.id, rest1.name);
    }
}
