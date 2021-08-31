package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static java.time.LocalDate.now;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.USER2_MAIL;
import static ru.javaops.topjava2.web.vote.VoteTestData.VOTE_MATCHER;

class ProfileVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void createWithLocation() throws Exception {
        VoteTo newVoteTo = new VoteTo(REST1_ID);
        Vote newVote = new Vote(null, now(), restaurantRepository.getById(newVoteTo.getRestaurantId()));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        Vote actual = voteRepository.getById(newId);
        VOTE_MATCHER.assertMatch(actual, newVote);
        REST_MATCHER.assertMatch(actual.getRestaurant(), rest1);
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void createIfExist() throws Exception {
        VoteTo newVoteTo = new VoteTo(REST3_ID);
        Vote newVote = new Vote(null, now(), restaurantRepository.getOne(newVoteTo.getRestaurantId()));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        Vote actual = voteRepository.getOne(newId);
        VOTE_MATCHER.assertMatch(actual, newVote);
        REST_MATCHER.assertMatch(actual.getRestaurant(), rest3);
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createInvalid() throws Exception {
        VoteTo newVoteTo = new VoteTo(NOT_FOUND);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}