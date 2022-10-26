package org.goravski.restaurantVoting.web.meal;


import org.goravski.restaurantVoting.RestaurantTestData;
import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.json.JsonUtil;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.service.MealService;
import org.goravski.restaurantVoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.nio.charset.StandardCharsets;

import static org.goravski.restaurantVoting.MealTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminMealControllerTest extends AbstractControllerTest {
    @Autowired
    private MealService service;

    @Test
    void getAll() throws Exception {
        String expected = JsonUtil.writeValues(meal1, meal2);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/meals")
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
        String expected = JsonUtil.writeValue(meal1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/meals/" + Meal1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(expected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/meals/" + Meal1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> service.get(Meal1_ID));
    }

    @Test
    void create() throws Exception {
        Meal newMeal = getNewMeal();
        newMeal.setRestaurant(RestaurantTestData.restaurant1);
        String newJson = JsonUtil.writeValue(newMeal);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newMeal))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        Assertions.assertEquals(newJson, mvcResult.getRequest().getContentAsString());
    }

    @Test
    void update() throws Exception {
        Meal updatedMeal = getUpdatedMeal();
        updatedMeal.setRestaurant(RestaurantTestData.restaurant1);
        String updatedJson = JsonUtil.writeValue(updatedMeal);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/meals/" + updatedMeal.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeFromObjectIgnoreFields(updatedMeal, "restaurant"))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        Assertions.assertEquals(updatedJson, mvcResult.getRequest().getContentAsString());
    }
}