package com.example.marsrover.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {

    private Rover rover;

    @BeforeEach
    void setup() {
        rover = new Rover(0,0, Direction.NORTH);
    }

    @ParameterizedTest
    @CsvSource({
            "'f,f,r,f,f',2,2",
            "'f,f,l,f,f',-2,2",
    })
    void testCommand(String commands, int x, int y) {
        rover.move(commands.split(","));
        assertEquals(rover.getCoordinate(), new Coordinate(x, y));
    }
}
