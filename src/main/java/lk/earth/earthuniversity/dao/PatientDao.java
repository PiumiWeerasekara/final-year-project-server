package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PatientDao extends JpaRepository<Patient, Integer> {

    Patient findByNic(String nic);

    Optional<Patient> findById(Integer id);

    @Query("select p from Patient p where p.id = :id")
    Patient findByMyId(@Param("id") Integer id);

    @Query("SELECT p from Patient p")
    List<Patient> findAllNameId();

    @Modifying
    @Transactional
    @Query("UPDATE Patient p SET p.status = 0 WHERE p.id = :id")
    void updateStatusAsinactive(@Param("id") Integer id);
}

