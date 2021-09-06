package ru.albabich.grad.to;

import lombok.*;
import ru.albabich.grad.model.Vote;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class RestaurantWithVotesTo extends NamedTo {
    private Integer voteCounter;

    public RestaurantWithVotesTo(Integer id, String name, List<Vote> votes) {
        super(id, name);
        this.voteCounter = votes.size();
    }
}
