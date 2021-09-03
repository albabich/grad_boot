package ru.albabich.grad.to;

import lombok.ToString;

import java.beans.ConstructorProperties;
import java.util.Objects;

@ToString(callSuper = true)
public class RestaurantWithVotesTo extends NamedTo {
    private final int voteCounter;

    @ConstructorProperties({"id", "name", "voteCounter"})
    public RestaurantWithVotesTo(int id, String name, int voteCounter) {
        super(id, name);
        this.voteCounter = voteCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantWithVotesTo that = (RestaurantWithVotesTo) o;
        return voteCounter == that.voteCounter && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voteCounter);
    }
}
