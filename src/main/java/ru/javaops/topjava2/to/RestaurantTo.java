package ru.javaops.topjava2.to;

import lombok.ToString;

import java.beans.ConstructorProperties;
import java.util.Objects;

@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {
    private final int votes;

    @ConstructorProperties({"id", "name", "votes"})
    public RestaurantTo(int id, String name, int votes) {
        super(id, name);
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return votes == that.votes && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, votes);
    }
}
