package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorScheduleDao extends JpaRepository<DoctorSchedule, Integer> {

    @Query("SELECT d from DoctorSchedule d")
    List<DoctorSchedule> findAllNameId();

}

