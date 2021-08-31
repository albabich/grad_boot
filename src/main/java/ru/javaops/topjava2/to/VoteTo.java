package ru.javaops.topjava2.to;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class VoteTo {
    @Range(min = 1)
    @NotNull
    private Integer restaurantId;
}
