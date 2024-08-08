package lk.earth.earthuniversity.dao;


import lk.earth.earthuniversity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    @Query("select u from User u where u.username = :name")
    User findByName(String name);
}
