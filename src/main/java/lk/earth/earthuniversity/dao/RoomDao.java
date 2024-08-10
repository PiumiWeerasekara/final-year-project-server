package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Patient;
import lk.earth.earthuniversity.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomDao extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r WHERE r.id = :id")
    Room findByMyId(@Param("id") Integer id);

    @Query("SELECT p FROM Room p")
    List<Patient> findAllNameId();

    @Query(value = "SELECT r.number FROM Room r ORDER BY r.id DESC LIMIT 1", nativeQuery = true)
    String findLastRoomNumber();
}

