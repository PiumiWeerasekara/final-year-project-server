package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorScheduleDao extends JpaRepository<DoctorSchedule,Integer> {

    //Optional<Schedule> findById(Integer id);
//    @Query("SELECT NEW DoctorSchedule (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
//    List<DoctorSchedule> findAllSchedules();
    @Query("SELECT d from DoctorSchedule d")
    List<DoctorSchedule> findAllNameId();
//
//    @Query("select e from Doctor e where e.id = :id")
//    Schedule findByMyId(@Param("id") Integer id);

    //@Query("SELECT NEW Doctor (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
//    @Query("SELECT s from Schedule s")
//    List<Schedule> findAllNameId();

}

