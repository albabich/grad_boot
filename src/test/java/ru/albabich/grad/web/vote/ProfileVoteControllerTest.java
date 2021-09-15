package ru.albabich.grad.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.Vote;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.repository.VoteRepository;
import ru.albabich.grad.util.validation.ValidationUtil;
import ru.albabich.grad.web.AbstractControllerTest;
import ru.albabich.grad.web.GlobalExceptionHandler;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.albabich.grad.web.restaurant.RestaurantTestData.*;
import static ru.albabich.grad.web.user.UserTestData.USER2_MAIL;
import static ru.albabich.grad.web.user.UserTestData.USER3_MAIL;
import static ru.albabich.grad.web.vote.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = new Vote(null, restaurantRepository.getById(REST1_ID));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(REST1_ID)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        Vote actual = voteRepository.getById(newId);
        VOTE_MATCHER.assertMatch(actual, newVote);
        assertThat(actual.getRestaurant().getId()).isEqualTo(REST1_ID);
    }

    @Test
    @WithUserDetails(value = USER3_MAIL)
    void createDuplicate() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(REST3_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE)));
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(NOT_FOUND)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER3_MAIL)
    void updateAfterCloseRevoting() throws Exception {
        LocalDateTime beforeCloseVoting = of(vote3.getVoteDate(), LocalTime.of(11, 1));
        ValidationUtil.TimeMachine.useFixedClockAt(beforeCloseVoting);
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE3_ID)
                .param("restaurantId", String.valueOf(REST3_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("You can't change vote after")));
    }

    @Test
    @WithUserDetails(value = USER3_MAIL)
    void updateBeforeCloseRevoting() throws Exception {
        Vote updated = new Vote(vote3);
        updated.setRestaurant(rest3);
        LocalDateTime beforeCloseVoting = of(vote3.getVoteDate(), LocalTime.of(10, 59));
        ValidationUtil.TimeMachine.useFixedClockAt(beforeCloseVoting);
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE3_ID)
                .param("restaurantId", String.valueOf(REST3_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote actual = voteRepository.getById(VOTE3_ID);
        VOTE_MATCHER.assertMatch(actual, updated);
        assertThat(actual.getRestaurant().getId()).isEqualTo(REST3_ID);
    }
}