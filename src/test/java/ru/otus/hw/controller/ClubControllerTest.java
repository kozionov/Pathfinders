package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.config.SecurityConfig;
import ru.otus.hw.dto.ClubDto;
import ru.otus.hw.services.ClubService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для ClubController.
 */
@WebMvcTest(ClubController.class)
@Import(SecurityConfig.class)
@DisplayName("Тесты ClubController")
class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    @Test
    @DisplayName("Получение списка клубов требует аутентификации")
    void getAllClubs_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/clubs"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "dir", roles = {"DIRECTOR"})
    @DisplayName("Получение списка клубов с аутентификацией")
    void getAllClubs_WithAuth_ShouldReturnOk() throws Exception {
        // Given
        ClubDto club1 = new ClubDto();
        club1.setId(1L);
        club1.setName("Test Club 1");
        club1.setCity("Moscow");

        ClubDto club2 = new ClubDto();
        club2.setId(2L);
        club2.setName("Test Club 2");
        club2.setCity("St. Petersburg");

        List<ClubDto> clubs = Arrays.asList(club1, club2);
        when(clubService.findAll()).thenReturn(clubs);

        // When & Then
        mockMvc.perform(get("/api/clubs")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Test Club 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Test Club 2"));
    }
}
