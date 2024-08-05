package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Employee;
import lk.earth.earthuniversity.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<Schedule, Integer> {

    //Optional<Schedule> findById(Integer id);;
//    @Query("SELECT NEW Schedule (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
//    List<Schedule> findAllSchedules();
//
//    @Query("select e from Doctor e where e.id = :id")
//    Schedule findByMyId(@Param("id") Integer id);

    //@Query("SELECT NEW Doctor (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
//    @Query("SELECT s from Schedule s")
//    List<Schedule> findAllNameId();
        @Query("SELECT s from Schedule s")
    List<Schedule> findAllNameId();

    //@Query("SELECT s FROM Schedule s WHERE s.id = :id")

    @Query("select s from Schedule s where s.id = :id")
    Schedule findByMyId(@Param("id") Integer id);


}

