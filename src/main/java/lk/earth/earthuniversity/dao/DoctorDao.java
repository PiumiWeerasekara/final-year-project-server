package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorDao extends JpaRepository<Doctor,Integer> {

//    Employee findByNumber(String number);
//    Employee findByNic(String nic);
    Optional<Doctor> findById(Integer id);

    @Query("select e from Doctor e where e.id = :id")
    Employee findByMyId(@Param("id") Integer id);

    @Query("SELECT NEW Doctor (d.id, d.title, d.firstName, d.lastName,d.nic, d.contactNo, d.address, d.speciality ) FROM Doctor d")
    List<Doctor> findAllNameId();

}

