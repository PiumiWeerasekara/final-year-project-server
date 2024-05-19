package lk.earth.earthuniversity.controller;
import lk.earth.earthuniversity.dao.DoctorDao;
import lk.earth.earthuniversity.dao.EmployeeDao;
import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    @Autowired
    private DoctorDao doctorDao;

    @GetMapping(path ="/list",produces = "application/json")
    public List<Doctor> get() {

        List<Doctor> doctors = this.doctorDao.findAllNameId();

        doctors = doctors.stream().map(
                doctor -> {
                    Doctor e = new Doctor(doctor.getId(), doctor.getTitle(), doctor.getFirstName(), doctor.getLastName(), doctor.getNic(), doctor.getContactNo(), doctor.getAddress(), doctor.getSpeciality());
                    return  e;
                }
        ).collect(Collectors.toList());

        return doctors;

    }
}




