package org.goravski.restaurantVoting.web.restaurant;

import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.json.JsonUtil;
import org.goravski.restaurantVoting.model.Restaurant;
import org.goravski.restaurantVoting.service.RestaurantService;
import org.goravski.restaurantVoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.goravski.restaurantVoting.MealTestData.*;
import static org.goravski.restaurantVoting.RestaurantTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantService service;

    @Test
    void getAll() throws Exception {
        String expected = JsonUtil.writeValues(restaurant1, restaurant2);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(expected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void getById() throws Exception {
        String expected = JsonUtil.writeValue(restaurant1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/restaurant/" + RESTAURANT1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(expected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void getWithMeals() throws Exception {
        restaurant1.setMeals(List.of(meal1));
        String expected = JsonUtil.writeValue(restaurant1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/restaurant/" + RESTAURANT1_ID + "/with-meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(expected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/restaurant/" + RESTAURANT1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent())
                .andDo(print());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void update() throws Exception {
        Restaurant restaurantUpdated = getUpdatedRestaurant();
        String jsonUpdated = JsonUtil.writeValue(restaurantUpdated);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/restaurant/" + RESTAURANT1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdated)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        Assertions.assertEquals(jsonUpdated, mvcResult.getRequest().getContentAsString());
    }

    @Test
    void create() throws Exception {
        Restaurant restaurantNew = getNewRestaurant();
        String jsonNew = JsonUtil.writeValue(restaurantNew);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/restaurant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNew)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        Assertions.assertEquals(jsonNew, mvcResult.getRequest().getContentAsString());
    }
}