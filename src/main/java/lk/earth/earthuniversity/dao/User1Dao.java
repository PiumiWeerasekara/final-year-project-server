package lk.earth.earthuniversity.dao;


import lk.earth.earthuniversity.entity.Staff;
import lk.earth.earthuniversity.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface User1Dao extends JpaRepository<User1,Integer> {
    User1 findByUsername(String username);

//    @Query("select u from User1 u where u.username = :name")
//    User1 findByName(String name);

    @Query("select s from User1 u inner join u.staff s where u.username = :name")
    Staff findByName(String name);
}
