package com.example.marsrover.api;

import com.example.marsrover.model.Direction;
import com.example.marsrover.model.Rover;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@RestController
public class MarsRoverController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    private static Map<String, Rover> allRovers = new ConcurrentHashMap<>();

    @PostMapping("/createRover")
    public @ResponseBody Rover createRover(@RequestParam("coordinates") String coordinates) {
        log.debug("received request {}", coordinates);
        if (coordinates == null) {
            return null;
        }
        String[] params = coordinates.split(",");
        if (params.length != 3) {
            return null;
        }

        try {
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            Direction d = Direction.fromAbbreviation(params[2]);
            Rover rover = new Rover(x, y, d);
            allRovers.put(rover.getRoverName(), rover);
            log.debug("added a new rover {}", rover);
            log.debug("total rover count {}", allRovers.size());
            return rover;
        } catch (IllegalArgumentException exception) {
            log.debug("exception caught", exception);
            return null;
        }
    }

    @PostMapping("/currentPosition")
    public @ResponseBody Rover currentPosition(@RequestParam("roverName") String roverName) {
        return allRovers.get(roverName);
    }

    @PostMapping("/moveRover")
    public @ResponseBody Rover moveRover(@RequestParam("roverName") String roverName, @RequestParam("command") String command) {
        Rover rover = allRovers.get(roverName);
        if (rover != null && Strings.isNotEmpty(command)) {
            String[] commands = command.split(",");
            rover.move(commands);
            return rover;
        }
        return null;
    }
}
