package lk.earth.earthuniversity.controller;


import lk.earth.earthuniversity.dao.User1Dao;
import lk.earth.earthuniversity.entity.Staff;
import lk.earth.earthuniversity.entity.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lk.earth.earthuniversity.entity.GlobalSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/users1")
public class User1Controller {

    @Autowired
    private User1Dao userdao;

    @GetMapping(produces = "application/json")
    public List<User1> get(@RequestParam HashMap<String, String> params) {
        List<User1> users = this.userdao.findAll();

        if (params.isEmpty()) {
            return users;
        }

        String name = params.get("name");
        String username = params.get("username");
        String typeId = params.get("type");

        Stream<User1> ustream = users.stream();

        if (name != null)
            ustream = ustream.filter(e -> e.getStaff().getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getStaff().getLastName().toLowerCase().contains(name.toLowerCase()));
        if (username != null) {
            ustream = ustream.filter(u -> u.getStaff().getFirstName().toLowerCase().contains(username.toLowerCase()));
        }
        if (typeId != null)
            ustream = ustream.filter(e -> e.getStaff().getStaffType().getId() == Integer.parseInt(typeId));

        return ustream.collect(Collectors.toList());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> add(@RequestBody User1 user){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        //User1 user = new User1();
        GlobalSetting.setUser(user);

       if(userdao.findByUsername(user.getUsername())!=null)
           errors = errors+"<br> Existing Username";

        if(errors==""){
//            for(Userrole u : user.getUserroles()) u.setUser(user);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Encrypt UserName and Password with Salt
            String salt = passwordEncoder.encode(user.getUsername());
            String hashedPassword = passwordEncoder.encode(salt + user.getPassword());
            user.setSalt(salt);
            user.setPassword(hashedPassword);
            userdao.save(user);

            responce.put("id",String.valueOf(user.getId()));
            responce.put("url","/users/"+user.getId());
            responce.put("errors",errors);

            return responce;
        }

        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(user.getId()));
        responce.put("url","/users/"+user.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody User1 user) {
        HashMap<String, String> response = new HashMap<>();

        String errors = "";

//        User extUser = userdao.findByUsername(user.getUsername());

//        if (extUser != null) {

            // Update Existing User Roles
            try {
//                extUser.getUserroles().clear();
//                user.getUserroles().forEach(newUserRole -> {
//                    newUserRole.setUser(extUser);
//                    extUser.getUserroles().add(newUserRole);
//                    newUserRole.setUser(extUser);
//                });

                // Update basic user properties
//                BeanUtils.copyProperties(user, extUser, "id","userroles");

//                userdao.save(extUser); // Save the updated extUser object

                response.put("id", String.valueOf(user.getId()));
                response.put("url", "/users/" + user.getId());
                response.put("errors", errors);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }

        return response;
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable String username){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        User1 use1 = userdao.findByUsername(username);

        if(use1==null)
            errors = errors+"<br> User Does Not Existed";

        if(errors=="") userdao.delete(use1);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("username",String.valueOf(username));
        responce.put("url","/users/"+username);
        responce.put("errors",errors);

        return responce;
    }
    @GetMapping(path ="/byName",produces = "application/json")
    public Staff get(@RequestParam("name") String name) {

        Staff staff = userdao.findByName(name);

        return staff;

    }
}
