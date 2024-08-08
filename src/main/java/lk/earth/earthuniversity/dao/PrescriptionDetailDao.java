package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Prescription;
import lk.earth.earthuniversity.entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDetailDao extends JpaRepository<Prescription,Integer> {

    @Query("SELECT p from PrescriptionDetail p where p.prescription = :id")
    List<PrescriptionDetail> findAllNameId(@Param("id") Integer id);

}

