package com.rz.java.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CapitalizeTest {

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldCapitalizeStringsAsExpected(String str, String expected) {
        assertEquals(expected, Capitalize.capitalizeWords(str));
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of(" ", ""),
                Arguments.of("i am FINE", "I Am FINE"),
                Arguments.of("mario rossi", "Mario Rossi"),
                Arguments.of("mario Rossi", "Mario Rossi"),
                Arguments.of("Mario rossi", "Mario Rossi"),
                Arguments.of("Mario Rossi", "Mario Rossi"),
                Arguments.of("rosanna d'amico", "Rosanna D'Amico"),
                Arguments.of("rosanna d'Amico", "Rosanna D'Amico"),
                Arguments.of("rosanna D'amico", "Rosanna D'Amico"),
                Arguments.of("rosanna D'Amico", "Rosanna D'Amico"),
                Arguments.of("mario della fiori", "Mario Della Fiori"),
                Arguments.of("giordano  maria  dell'era ", "Giordano Maria Dell'Era"),
                Arguments.arguments("       gimme    more   spaces    ", "Gimme More Spaces"));
    }
}