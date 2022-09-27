package org.goravski.restaurantVoting.web.meal;


import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.json.JsonUtil;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.service.MealService;
import org.goravski.restaurantVoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.goravski.restaurantVoting.MealTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminMealControllerTest extends AbstractControllerTest {
    @Autowired
    private MealService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/meals/" + Meal1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        Meal updatedMeal = getUpdatedMeal();
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/meals/" + updatedMeal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeIgnoreFields(updatedMeal, "restaurant")))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}