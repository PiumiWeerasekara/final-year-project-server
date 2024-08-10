package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Drug;
import lk.earth.earthuniversity.entity.Prescription;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrugDao extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d from Drug d")
    List<Drug> findAllNameId();

}

