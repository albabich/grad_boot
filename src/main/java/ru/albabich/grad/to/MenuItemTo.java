package ru.albabich.grad.to;

import lombok.*;

import javax.validation.constraints.Digits;
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
    @Digits(integer = 7, fraction = 2)
    private double price;

    private LocalDate localDate;

    public MenuItemTo(String name, double price) {
        super(null, name);
        this.price = price;
    }

    public MenuItemTo(Integer id, LocalDate date, String name, double price) {
        super(id, name);
        this.price = price;
        this.localDate = date;
    }
}
