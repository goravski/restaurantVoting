package org.goravski.restaurantVoting.web.vote;

import org.goravski.restaurantVoting.UserTestData;
import org.goravski.restaurantVoting.model.Vote;
import org.goravski.restaurantVoting.service.VoteService;
import org.goravski.restaurantVoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.goravski.restaurantVoting.RestaurantTestData.*;
import static org.goravski.restaurantVoting.web.vote.VoteController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.goravski.restaurantVoting.VoteTestData.*;
import static org.goravski.restaurantVoting.json.JsonUtil.*;


class VoteControllerTest extends AbstractControllerTest {
    private static final String URL = VoteController.VOTE_URL + '/';
    @Autowired
    private VoteService voteService;

    @Test
    void updateVoteTest() throws Exception {
        Vote updatedVote = getUpdatedVote(ACCEPTABLE_TIME);
        String jsonUpdated = writeFromObjectIgnoreFields(updatedVote, "restaurant", "user");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(URL + updatedVote.getId())
                        .content(jsonUpdated)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        assertThat(mvcResult.getRequest().getContentAsString()).isEqualTo(jsonUpdated);
    }

    @Test
    void createVoteTest() throws Exception {
        Vote newVote = newVote();
        newVote.setRestaurant(restaurant1);
        newVote.setUser(UserTestData.user);
        String jsonNew = writeValue(newVote);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL)
                        .content(jsonNew)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        Vote createdVote = readValue(mvcResult.getResponse().getContentAsString(), Vote.class);
        int id = createdVote.getId();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(writeValue(voteService.get(id)));
    }

    @Test
    void CountRestaurantVoteTest() throws Exception {
        String expected = "2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL + "restaurant/" + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        Assertions.assertEquals(expected, mvcResult.getResponse().getContentAsString());
    }
}