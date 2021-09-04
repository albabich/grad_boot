package ru.albabich.grad.web.vote;

import ru.albabich.grad.model.Vote;
import ru.albabich.grad.web.MatcherFactory;

import java.time.Month;
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static ru.albabich.grad.web.restaurant.RestaurantTestData.*;
import static ru.albabich.grad.web.user.UserTestData.user2;
import static ru.albabich.grad.web.user.UserTestData.user3;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final int VOTE1_ID = 1;

    public static final Vote vote1 = new Vote(VOTE1_ID, now(), rest1);
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, of(2021, Month.APRIL, 5), rest2);
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, now(), rest2);
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, now(), rest3);
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, of(2021, Month.APRIL, 6), rest2);
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, of(2021, Month.APRIL, 6), rest3);

    public static final List<Vote> voteRest1Today = List.of(vote1);
    public static final List<Vote> voteRest2Today = List.of(vote2);
    public static final List<Vote> voteRest3Today = List.of(vote4);

}
