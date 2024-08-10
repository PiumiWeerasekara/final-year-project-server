package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.RoomDao;
import lk.earth.earthuniversity.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @GetMapping(produces = "application/json")
    public List<Room> get(@RequestParam HashMap<String, String> params) {

        List<Room> rooms = this.roomDao.findAll();

        if (params.isEmpty()) return rooms;

        Stream<Room> estream = rooms.stream();

        return estream.collect(Collectors.toList());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> save(@RequestBody Room room) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";


        if (errors == "") roomDao.save(room);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(room.getId()));
        responce.put("url", "/room/" + room.getId());
        responce.put("errors", errors);

        return responce;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> delete(@PathVariable Integer id) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Room r = roomDao.findByMyId(id);

        if (r == null)
            errors = errors + "<br> Room Does Not Existed";

        if (errors == "") roomDao.delete(r);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(id));
        responce.put("url", "/room/" + id);
        responce.put("errors", errors);

        return responce;
    }

    @GetMapping(path = "/number", produces = "text/plain")
    public String get() {


        String lastRoomNumber = roomDao.findLastRoomNumber();

        if (lastRoomNumber == null || lastRoomNumber.isEmpty()) {
            return "00001";
        }

        int nextRoomNumberInt;
        try {
            nextRoomNumberInt = Integer.parseInt(lastRoomNumber) + 1;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid room number format: " + lastRoomNumber, e);
        }
        return String.format("%05d", nextRoomNumberInt);
    }
}




