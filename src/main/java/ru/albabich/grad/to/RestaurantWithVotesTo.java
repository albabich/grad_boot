package ru.albabich.grad.to;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class RestaurantWithVotesTo extends NamedTo {
    private int voteCounter;

    public RestaurantWithVotesTo(int id, String name, int voteCounter) {
        super(id, name);
        this.voteCounter = voteCounter;
    }
}
