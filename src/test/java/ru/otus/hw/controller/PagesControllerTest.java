package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.hw.security.SecurityConfiguration;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("PagesController should")
@WebMvcTest(PagesController.class)
@Import(SecurityConfiguration.class)
public class PagesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @BeforeAll
    public static void before() {

    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }


    @Test
    @DisplayName("have access to url with authenticated user")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void shouldHaveAccessWithPages() throws Exception {
        mockMvc.perform(get("/club/create")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("not have access to url without authenticated user")
    void shouldNotHaveAccessWithoutPages() throws Exception {
        mockMvc.perform(get("/club/create")).andExpect(status().is(302));
    }



}
