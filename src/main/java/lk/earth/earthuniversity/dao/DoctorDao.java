package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DoctorDao extends JpaRepository<Doctor,Integer> {

//    Doctor findByNumber(String number);
    Doctor findByNic(String nic);
    Optional<Doctor> findById(Integer id);

    @Query("select e from Doctor e where e.id = :id")
    Doctor findByMyId(@Param("id") Integer id);

    //@Query("SELECT NEW Doctor (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
    @Query("SELECT d from Doctor d")
    List<Doctor> findAllNameId();

    @Modifying
    @Transactional
    @Query("UPDATE Doctor s SET s.status = 0 WHERE s.id = :id")
    void updateStatusAsinactive(@Param("id") Integer id);
}

