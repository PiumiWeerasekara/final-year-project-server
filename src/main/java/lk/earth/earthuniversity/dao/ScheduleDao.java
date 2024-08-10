package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<Schedule, Integer> {

    @Query("SELECT s from Schedule s")
    List<Schedule> findAllNameId();

    @Query("select s from Schedule s where s.id = :id")
    Schedule findByMyId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.status = 0 WHERE s.id = :id")
    void updateStatus(@Param("id") Integer id);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId " +
            "AND s.scheduleDate = :scheduleDate " +
            "AND ((s.startTime < :endTime AND s.endTime > :startTime) " +
            "OR (s.startTime < :endTime AND s.endTime >= :endTime) " +
            "OR (s.startTime <= :startTime AND s.endTime > :startTime)) " +
            "AND s.status = 1")
    List<Schedule> checkDoctorAvailability(@Param("doctorId") Integer doctorId,
                                           @Param("scheduleDate") java.util.Date scheduleDate,
                                           @Param("startTime") String startTime,
                                           @Param("endTime") String endTime);

    @Query(value = "SELECT s.* FROM Schedule s WHERE s.scheduleDate > CURRENT_DATE AND s.status=1 AND s.doctor_id = :id", nativeQuery = true)
    List<Schedule> findUpComingSchedulesByDoctorID(Integer id);
}

