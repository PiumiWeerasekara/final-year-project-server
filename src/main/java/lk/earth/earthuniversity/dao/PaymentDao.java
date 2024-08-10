package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentDao extends JpaRepository<Payment, Integer> {

    @Query("select p from Payment p where p.prescription.id = :id")
    Optional<Payment> findByPrescriptionId(Integer id);

    @Query("select p from Payment p where p.id = :id")
    Payment findByMyId(@Param("id") Integer id);

    @Query("SELECT p from Payment p")
    List<Payment> findAllNameId();

    @Query(value = "SELECT MONTH(a.billDate) as month, SUM(a.amount) as visitCount " +
            "FROM Payment a " +
            "WHERE YEAR(a.billDate) = YEAR(CURDATE()) " +
            "GROUP BY MONTH(a.billDate)",
            nativeQuery = true)
    List<Object[]> getTotalByMonth();
}

