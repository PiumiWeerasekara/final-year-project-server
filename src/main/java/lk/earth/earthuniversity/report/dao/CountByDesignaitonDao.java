package lk.earth.earthuniversity.report.dao;

import lk.earth.earthuniversity.report.entity.CountByDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;

public interface CountByDesignaitonDao extends JpaRepository<CountByDesignation,Integer> {

    //@Query(value = "SELECT NEW CountByDesignation(d.name, COUNT(e.fullname)) FROM Employee e, Designation d WHERE e.designation.id = d.id GROUP BY d.id")
//    @Query(value = "SELECT NEW CountByDesignation(d.firstName, d.lastName, COUNT(a.id)) " +
//            "FROM Doctor d " +
//            "JOIN d.schedule s " + // Changed d.Schedule to d.schedule
//            "LEFT JOIN s.appointments a " +
//            "GROUP BY d.id " +
//            "ORDER BY COUNT(a.id) DESC")
    //@Query(value = "SELECT NEW CountByDesignation(d.firstName, d.lastName, COUNT(a.id)) FROM Doctor d inner join Schedule  s on  d.id=s.doctorId LEFT JOIN s.appointments a GROUP BY d.id ORDER BY COUNT(a.id) DESC")
    //@Query(value = "SELECT NEW CountByDesignation(d.firstName, d.lastName, COUNT(d.id)) FROM Doctor d")
//    @Query(value = "SELECT NEW CountByDesignation(d.firstName, d.lastName, COUNT(a.id)) " +
//            "FROM Doctor d " +
//            "INNER JOIN Schedule s ON d.id = s.doctorId " +
//            "LEFT JOIN s.appointments a " +
//            "GROUP BY d.id " +
//            "ORDER BY COUNT(a.id) DESC")
//
//    List<CountByDesignation> countByDesignation();

    Logger logger = LoggerFactory.getLogger(CountByDesignaitonDao.class);

    @Query(value = "SELECT NEW CountByDesignation(d.firstName, d.lastName, COUNT(a.id)) " +
            "FROM Doctor d " +
            "INNER JOIN Schedule s ON d.id = s.doctor.id " +  // Joining the Doctor entity directly
            "LEFT JOIN Appointment a ON s.id = a.schedule.id " +  // Adjusted the join condition to use the schedule association in Appointment entity
            "WHERE MONTH(s.scheduleDate) = MONTH(CURDATE())" +
            "GROUP BY d.id " +
            "ORDER BY COUNT(a.id) DESC")
    List<CountByDesignation> countByDesignation();

    default void logQueryUpdate() {
        // Log the query
        logger.info("Query updated in CountByDesignationDao: countByDesignation");
    }



}

