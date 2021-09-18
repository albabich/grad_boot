package ru.albabich.grad.to;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class MenuItemTo extends NamedTo {

    @NotNull
    @Positive
    private int price;

    @NotNull
    private LocalDate available;

    public MenuItemTo(LocalDate available, String name, int price) {
        super(null, name);
        this.price = price;
        this.available = available;
    }

    public MenuItemTo(Integer id, LocalDate available, String name, int price) {
        super(id, name);
        this.price = price;
        this.available = available;
    }
}
