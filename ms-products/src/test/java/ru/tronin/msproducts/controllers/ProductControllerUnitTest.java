package ru.tronin.msproducts.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tronin.msproducts.MsProductsApplication;
import ru.tronin.msproducts.models.entities.Category;
import ru.tronin.msproducts.models.entities.Product;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void showPaginatedProductsTest() throws Exception {
//        Request without parameters
        mockMvc.perform(get("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].name", is("test1")));
//        Min & max
        mockMvc.perform(get("/api/v1/products?min_price=4&max_price=6")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("test3")));

//        Searching test
        mockMvc.perform(get("/api/v1/products?name_part=test1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("test1")));
//        All parameters Searching test
        mockMvc.perform(get("/api/v1/products?name_part=3&min_price=4&max_price=6")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("test3")));

//        Failled min & max test
//        min and max nothing found
        mockMvc.perform(get("/api/v1/products?min_price=13&max_price=42")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
        //        when min > max
        mockMvc.perform(get("/api/v1/products?min_price=8&max_price=2")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
        // illegal type of argument
        mockMvc.perform(get("/api/v1/products?min_price=testword&max_price=42")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void editProductTest() throws Exception {
        mockMvc.perform(get("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test1")))
                .andExpect(jsonPath("$.description", is("description1")))
                .andExpect(jsonPath("$.cost", is(12.0)))
                .andExpect(jsonPath("$.category", is("category1")));
    }


    @Test
    public void createProductTest() throws Exception {
        Product product =  new Product("test17", "description", 12.32, null);
        mockMvc.perform((post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writer().writeValueAsBytes(product))))
                .andExpect(status().isCreated());
        mockMvc.perform((post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().writeValueAsBytes(null))))
                .andExpect(status().is4xxClientError());

    }


}
