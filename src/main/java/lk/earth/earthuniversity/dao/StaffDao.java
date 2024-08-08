package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StaffDao extends JpaRepository<Staff, Integer> {

    Staff findByNic(String nic);

    Optional<Staff> findById(Integer id);

    @Query("select e from Staff e where e.id = :id")
    Staff findByMyId(@Param("id") Integer id);

    @Query("SELECT d from Staff d")
    List<Staff> findAllNameId();

    @Modifying
    @Transactional
    @Query("UPDATE Staff s SET s.status = 0 WHERE s.id = :id")
    void updateStatusAsinactive(@Param("id") Integer id);
}

