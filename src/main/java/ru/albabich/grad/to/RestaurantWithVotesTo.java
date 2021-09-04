package ru.albabich.grad.to;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class RestaurantWithVotesTo extends NamedTo {
    private Integer voteCounter;

    public RestaurantWithVotesTo(Integer id, String name, Integer voteCounter) {
        super(id, name);
        this.voteCounter = voteCounter;
    }
}
