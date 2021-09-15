package ru.albabich.grad.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.albabich.grad.error.VoteException;
import ru.albabich.grad.model.Vote;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.repository.UserRepository;
import ru.albabich.grad.repository.VoteRepository;
import ru.albabich.grad.util.validation.ValidationUtil;
import ru.albabich.grad.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/votes";

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/today")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(voteRepository.getByDateAndUser(LocalDate.now(), authUser.id()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(voteRepository.getByIdAndUserAndDate(id, authUser.id(), LocalDate.now()));
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {

        int userId = authUser.id();
        Vote vote = new Vote(null, restaurantRepository.getById(restaurantId));

        log.info("create vote {} for user {} for restaurant {}", vote, userId, restaurantId);

        vote.setUser(userRepository.getById(userId));

        Vote created = voteRepository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {

        int userId = authUser.id();

        log.info("update vote {} for user {} for restaurant {}", id, userId, restaurantId);

        Vote vote = voteRepository.checkBelongAndDate(id, userId, LocalDate.now());
        ValidationUtil.checkChangeVoteAbility();

        vote.setRestaurant(restaurantRepository.getById(restaurantId));
        voteRepository.save(vote);
    }
}
