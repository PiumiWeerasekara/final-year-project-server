package lk.earth.earthuniversity.security;

import lk.earth.earthuniversity.dao.User1Dao;
import lk.earth.earthuniversity.dao.UserDao;
import lk.earth.earthuniversity.entity.GlobalSetting;
import lk.earth.earthuniversity.entity.Privilege;
import lk.earth.earthuniversity.entity.User1;
import lk.earth.earthuniversity.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class User1Service implements UserDetailsService {

    final User1Dao userdao;
    private static final String[] VALID_USERNAMES = {"AdminDMC", "RecepDMC", "DoctoDMC", "CountDMC"};
    @Autowired
    public User1Service(User1Dao userdao) {
        this.userdao = userdao;
    }

    public User1 getByUsername(String username){

        User1 user = new User1();

        if (isValidUsername(username)){

            user.setUsername(username);

        }else {
            user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }

        return user;
    }

    private boolean isValidUsername(String username) {
        for (String validUsername : VALID_USERNAMES) {
            if (validUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

//        if (username.equals("AdminEUC")) {
//            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//            authorities.add(new SimpleGrantedAuthority("gender-list-get"));
//            authorities.add(new SimpleGrantedAuthority("designation-list-get"));
//            authorities.add(new SimpleGrantedAuthority("employeestatus-list-get"));
//            authorities.add(new SimpleGrantedAuthority("employee-select"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(GlobalSetting.getUserName())
                    .password(new BCryptPasswordEncoder().encode("Admin1234"))
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
//        }
//        else {
//
//            User1 user = userdao.findByUsername(username);
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found with username: " + username);
//            }
//
////            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//
////            List<Userrole> userroles = (List<Userrole>) user.getUserroles();
//
////            for(Userrole u : userroles){
////                List<Privilege> privileges = (List<Privilege>) u.getRole().getPrivileges();
////                for (Privilege p:privileges){
////                    authorities.add(new SimpleGrantedAuthority(p.getAuthority()));
////                }
////            }
//
//            return org.springframework.security.core.userdetails.User
//                    .withUsername(user.getUsername())
//                    .password(new BCryptPasswordEncoder().encode(user.getPassword()))
//                    .authorities(authorities)
//                    .accountExpired(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .disabled(false)
//                    .build();
//        }
    }
}
