package ru.albabich.grad.to;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class RestaurantWithMenuTo extends NamedTo {
    private List<MenuItemTo> menuItems;

    public RestaurantWithMenuTo(Integer id, String name, List<MenuItemTo> menuItems) {
        super(id, name);
        this.menuItems = menuItems;
    }
}
