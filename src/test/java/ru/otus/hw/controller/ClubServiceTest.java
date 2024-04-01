package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.services.impl.LogServiceImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис создания клуба ")
public class ClubServiceTest {

    @Test
    @DisplayName("должен определять вторую половину года")
    public void testDateInSecondHalfOfYear() {
        boolean between = LogServiceImpl.isBetween(9, 1, 7, 15, 12, 31);
        assertThat(between).isEqualTo(true);
        boolean between2 = LogServiceImpl.isBetween(5, 1, 7, 15, 12, 31);
        assertThat(between2).isEqualTo(false);
    }

    @Test
    @DisplayName("должен определять первую половину года ")
    public void testDateInFirstHalfOfY2ar() {
        boolean between = LogServiceImpl.isBetween(5, 1, 1, 1, 7, 14);
        assertThat(between).isEqualTo(true);
        boolean between2 = LogServiceImpl.isBetween(8, 1, 1, 1, 7, 14);
        assertThat(between2).isEqualTo(false);
    }

    @Test
    @DisplayName("должен определять первую половину года2 ")
    public void testDateInFirstHalfOfYear2() {
        boolean between = LogServiceImpl.isFirstHalfOfYear(LocalDate.of(2024, 10, 10));
        assertThat(between).isEqualTo(false);
        boolean between2 = LogServiceImpl.isFirstHalfOfYear(LocalDate.of(2024, 5, 10));
        assertThat(between2).isEqualTo(true);
    }
}
