package com.example.marsrover.model;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Rover {

    private String roverName;
    private Coordinate coordinate;
    private Direction direction;

    private static int ROVER_ID = 0;

    private static final Map<Direction, Coordinate> FORWARD_DELTA = new HashMap<>();
    static {
        FORWARD_DELTA.put(Direction.NORTH, new Coordinate(0, 1));
        FORWARD_DELTA.put(Direction.SOUTH, new Coordinate(0, -1));
        FORWARD_DELTA.put(Direction.EAST, new Coordinate(1, 0));
        FORWARD_DELTA.put(Direction.WEST, new Coordinate(-1, 0));
    }

    public Rover(int x, int y, Direction direction) {
        this.coordinate = new Coordinate(x, y);
        this.direction = direction;
        this.roverName = String.format("R%d", getNextId());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    private synchronized int getNextId() {
        ROVER_ID++;
        return ROVER_ID;
    }

    public String getRoverName() {
        return roverName;
    }

    public void move(String[] commands) {
        for (String c: commands) {
            if ("r".equalsIgnoreCase(c)) {
                rotateClockWise();
            } else if ("l".equalsIgnoreCase(c)) {
                rotateAntiClockWise();
            } else if ("f".equalsIgnoreCase(c)) {
                moveForward();
            } else if ("b".equalsIgnoreCase(c)){
                moveBackward();
            } else {
                break;
            }
        }
    }

    private void rotateClockWise() {
        if (this.direction == Direction.NORTH) {
            this.direction = Direction.EAST;
        } else if (this.direction == Direction.EAST) {
            this.direction = Direction.SOUTH;
        }else if (this.direction == Direction.SOUTH) {
            this.direction = Direction.WEST;
        } else if (this.direction == Direction.WEST) {
            this.direction = Direction.NORTH;
        }
    }

    private void rotateAntiClockWise() {
        if (this.direction == Direction.NORTH) {
            this.direction = Direction.WEST;
        } else if (this.direction == Direction.WEST) {
            this.direction = Direction.SOUTH;
        }else if (this.direction == Direction.SOUTH) {
            this.direction = Direction.EAST;
        } else if (this.direction == Direction.EAST) {
            this.direction = Direction.NORTH;
        }
    }

    private void moveForward() {
        Coordinate delta = FORWARD_DELTA.get(this.direction);
        this.coordinate.setX(this.coordinate.getX() + delta.getX());
        this.coordinate.setY(this.coordinate.getY() + delta.getY());
    }

    private void moveBackward() {
        Coordinate delta = FORWARD_DELTA.get(this.direction);
        this.coordinate.setX(this.coordinate.getX() - delta.getX());
        this.coordinate.setY(this.coordinate.getY() - delta.getY());
    }
}
