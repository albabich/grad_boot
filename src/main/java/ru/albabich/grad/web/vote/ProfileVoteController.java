package ru.albabich.grad.web.vote;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.albabich.grad.error.NotFoundException;
import ru.albabich.grad.model.Vote;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.repository.UserRepository;
import ru.albabich.grad.repository.VoteRepository;
import ru.albabich.grad.util.validation.ValidationUtil;
import ru.albabich.grad.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

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
        return ResponseEntity.of(voteRepository.getByIdAndUser(id, authUser.id()));
    }

    @GetMapping()
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return voteRepository.getAllForUser(authUser.id());
    }

    @Transactional
    @PostMapping()
    @RequestBody(content = @Content)
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
    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {

        int userId = authUser.id();
        Vote todayVote = voteRepository.getByDateAndUser(LocalDate.now(), authUser.id()).orElseThrow(()-> new NotFoundException("You have not vote today"));
        log.info("update today vote {} for user {} for restaurant {}", todayVote.id(), userId, restaurantId);

        ValidationUtil.checkRevoteAbility();

        todayVote.setRestaurant(restaurantRepository.getById(restaurantId));
        voteRepository.save(todayVote);
    }
}
