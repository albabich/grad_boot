package ru.albabich.grad.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.albabich.grad.model.Vote;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.repository.UserRepository;
import ru.albabich.grad.repository.VoteRepository;
import ru.albabich.grad.to.VoteTo;
import ru.albabich.grad.util.validation.ValidationUtil;
import ru.albabich.grad.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ProfileVoteController(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Vote> createWithLocation(@RequestBody @Valid VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {

        int userId = authUser.id();
        int restaurantId = voteTo.getRestaurantId();
        Vote vote = new Vote(null, restaurantRepository.getById(restaurantId));

        log.info("create vote {} for user {} for restaurant {}", vote, userId, restaurantId);

        vote.setUser(userRepository.getById(userId));

        Vote todayVote = voteRepository.getByDateAndUser(LocalDate.now(), userId);
        if (todayVote != null) {
            ValidationUtil.checkChangeVoteAbility();
            vote.setId(todayVote.id());
        }

        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
