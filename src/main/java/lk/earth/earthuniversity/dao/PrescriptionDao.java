package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao extends JpaRepository<Prescription,Integer> {

    Optional<Prescription> findByAppointmentId(Integer appointmentId);

    @Query("select p from Prescription p where p.appointment.id = :id")
    Optional<Prescription> findByAppointment(Integer id);
    @Query("select p from Prescription p where p.id = :id")
    Prescription findByMyId(@Param("id") Integer id);

    //@Query("SELECT NEW Doctor (d.id, d.title, d.firstName, d.lastName, d.photo, d.dob, d.nic, d.address, d.contactNo, d.email, d.gender, d.speciality, d.medicalLicenseNo, d.licenseEXPDate ) FROM Doctor d")
    @Query("SELECT p from Prescription p WHERE p.status = 1")
    List<Prescription> findAllNameId();

    @Query(value = "SELECT p.referenceNo FROM Prescription p ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    String findLastNumber();
}

