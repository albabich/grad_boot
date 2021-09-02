package ru.albabich.grad.to;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class MenuItemTo extends NamedTo {

    @NotNull
    @Range(min = 1)
    private  int price;

    public MenuItemTo(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public MenuItemTo(String name, int price) {
        super(null, name);
        this.price = price;
    }
}
