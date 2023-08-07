package com.example.marsrover.model;

public enum Direction {
    NORTH("North", "N"),
    EAST("East", "E"),
    SOUTH("South", "S"),
    WEST("West", "W");

    private final String displayName;
    private final String abbreviation;

    Direction(String displayName, String abbreviation) {
        this.displayName = displayName;
        this.abbreviation = abbreviation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Direction fromAbbreviation(String abbreviation) throws IllegalArgumentException {
        for (Direction direction : Direction.values()) {
            if (direction.abbreviation.equals(abbreviation)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid abbreviation: " + abbreviation);
    }
}