package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Appointment;
import lk.earth.earthuniversity.entity.AppointmentSearch;
import lk.earth.earthuniversity.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

public interface AppointmentDao extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a from Appointment a")
    List<Appointment> findAllNameId();

    @Query(value = "SELECT a.appointmentNo FROM Appointment a WHERE a.schedule_id = :scheduleId ORDER BY a.id DESC LIMIT 1", nativeQuery = true)
    String findLastAppointmentNumber(@Param("scheduleId") int scheduleId);

    @Query(value = "SELECT s.scheduleDate, s.startTime, COALESCE(MAX(a.appointmentNo) + 1, 1) AS appointmentNo, s.id, s.doctor_id, d.speciality_id, s.noOfPatient FROM schedule s LEFT JOIN appointment a ON s.id = a.schedule_id INNER JOIN Doctor d ON s.doctor_id = d.id WHERE (s.scheduleDate > CURRENT_DATE OR (s.scheduleDate = CURRENT_DATE AND s.endTime > CURRENT_TIME)) AND s.status = 1 GROUP BY  s.id ORDER BY s.scheduleDate ASC", nativeQuery = true)
    List<Object[]> findAllAvailable();

    @Query("SELECT a FROM Appointment a WHERE a.schedule.id = :scheduleId ORDER BY a.id DESC")
    List<Appointment> findLastAppointment(@Param("scheduleId") int scheduleId);

    @Modifying
    @Transactional
    @Query("UPDATE Appointment s SET s.status = 0 WHERE s.id = :id")
    void updateStatus(@Param("id") Integer id);

    @Query(value = "SELECT a.* FROM Appointment a INNER JOIN Schedule s ON a.schedule_id = s.id WHERE s.scheduleDate = CURRENT_DATE AND CURRENT_TIME BETWEEN s.startTime AND s.endTime AND s.status=1 AND s.doctor_id = :id ORDER BY a.appointmentNo ASC ", nativeQuery = true)
    List<Appointment> findMyCurrentScheduleAppointments(@Param("id") Integer id);


    @Modifying
    @Transactional
    @Query("UPDATE Appointment s SET s.status = 3 WHERE s.id = :id")
    void updateStatusAsPrescribed(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Appointment s SET s.status = 4 WHERE s.id = :id")
    void updateStatusAsPayed(@Param("id") Integer id);

    @Query(value = "SELECT a.* FROM Appointment a WHERE a.appointmentDate > CURRENT_DATE AND a.status=1 AND a.patient_id = :id", nativeQuery = true)
    List<Appointment> findUpcomingAppointmentsByPatientID(Integer id);


    //Dashboad details
    @Query(value = "SELECT a.* FROM Appointment a  INNER JOIN Schedule s ON a.schedule_id = s.id  WHERE s.scheduleDate = CURRENT_DATE AND CURRENT_TIME BETWEEN s.startTime AND s.endTime AND a.appointmentNo = (SELECT MAX(a2.appointmentNo) FROM Appointment a2 WHERE a2.schedule_id = s.id )", nativeQuery = true)
    List<Appointment> getAllOnGoingSchedules();

    @Query(value = "SELECT a.* FROM Appointment a  INNER JOIN Schedule s ON a.schedule_id = s.id  WHERE s.scheduleDate = CURRENT_DATE AND CURRENT_TIME BETWEEN s.startTime AND s.endTime  AND s.status = 3 group by s.id order by appointmentNo desc limit 1", nativeQuery = true)
    List<Appointment> getNext();

    @Query(value = "SELECT a.* FROM Appointment a INNER JOIN Schedule s ON a.schedule_id = s.id WHERE (s.scheduleDate = CURRENT_DATE AND CURRENT_TIME < s.startTime) OR (s.scheduleDate > CURRENT_DATE) AND s.status=1 AND s.doctor_id = :id ORDER BY a.appointmentNo ASC ", nativeQuery = true)
    List<Appointment> findMyUpcomingScheduleAppointments(@Param("id") Integer id);

    @Query(value = "SELECT MONTH(a.appointmentDate) as month, COUNT(a.id) as visitCount " +
            "FROM Appointment a " +
            "WHERE a.patient_id = :id AND YEAR(a.appointmentDate) = YEAR(CURDATE()) " +
            "GROUP BY MONTH(a.appointmentDate)",
            nativeQuery = true)
    List<Object[]> getVisitCountForCurrentYear(@Param("id") Integer id);





}

