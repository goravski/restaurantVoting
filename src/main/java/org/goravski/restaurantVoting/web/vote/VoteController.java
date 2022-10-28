package org.goravski.restaurantVoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Vote;
import org.goravski.restaurantVoting.service.VoteService;
import org.goravski.restaurantVoting.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = VoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public final static String VOTE_URL = "/votes";
    @Autowired
    private VoteService voteService;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody Vote eVote) {
        ValidationUtil.assureIdConsistent(eVote, id);
        voteService.update(eVote);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@RequestBody Vote eVote) {
        Vote created = voteService.create(eVote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/{id}")
    public Vote getById(@PathVariable int id) {
        return voteService.get(id);
    }

    @GetMapping(value = "/restaurant/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int countRestaurantVote (@PathVariable int id){
        return voteService.countVotes(id);
    }
}
