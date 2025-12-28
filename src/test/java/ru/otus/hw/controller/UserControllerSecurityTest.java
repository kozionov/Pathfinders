package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.SecurityConfig;
import ru.otus.hw.services.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController security should")
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("allow admin to access users API")
    @WithMockUser(roles = "ADMIN")
    void shouldAllowAdminToAccessUsersApi() throws Exception {
        when(userService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("deny director to access users API")
    @WithMockUser(roles = "DIRECTOR")
    void shouldDenyDirectorToAccessUsersApi() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isForbidden());
    }
}
