package lk.earth.earthuniversity.controller;
import lk.earth.earthuniversity.dao.DoctorDao;
import lk.earth.earthuniversity.dao.EmployeeDao;
import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Employee;
import lk.earth.earthuniversity.entity.Gender;
import lk.earth.earthuniversity.entity.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Doctor> get(@RequestParam HashMap<String, String> params) {

        List<Doctor> doctors = this.doctorDao.findAll();

        if(params.isEmpty())  return doctors;

        String name= params.get("name");
        String nic= params.get("nic");
        String specialityId= params.get("specialityId");
        Stream<Doctor> estream = doctors.stream();

        if(specialityId!=null) estream = estream.filter(e -> e.getSpeciality().getId()==Integer.parseInt(specialityId));
        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
        if(name!=null) estream = estream.filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name));

        return estream.collect(Collectors.toList());

    }
    @GetMapping(path ="/list",produces = "application/json")
    public List<Doctor> get() {

        List<Doctor> doctors = this.doctorDao.findAllNameId();

        doctors = doctors.stream().map(
                doctor -> {
                    Doctor d = new Doctor(doctor.getId(), doctor.getTitle(), doctor.getFirstName(), doctor.getLastName(), doctor.getPhoto(), doctor.getDob(), doctor.getNic(), doctor.getAddress(), doctor.getContactNo(), doctor.getEmail(), doctor.getGender(), doctor.getSpeciality(), doctor.getMedicalLicenseNo(), doctor.getLicenseEXPDate());
                    return  d;
                }
        ).collect(Collectors.toList());

        return doctors;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> save(@RequestBody Doctor doctor){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Doctor doc = doctorDao.findByNic(doctor.getNic());

//        if(emp1!=null && employee.getId()!=emp1.getId())
//            errors = errors+"<br> Existing Number";
        if(doc!=null && doctor.getId()!=doc.getId())
            errors = errors+"<br> Existing NIC";

        if(errors=="") doctorDao.save(doctor);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(doctor.getId()));
        responce.put("url","/doctor/"+doctor.getId());
        responce.put("errors",errors);

        return responce;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Doctor doc = doctorDao.findByMyId(id);

        if(doc==null)
            errors = errors+"<br> Doctor Does Not Existed";

        if(errors=="") doctorDao.delete(doc);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/doctor/"+id);
        responce.put("errors",errors);

        return responce;
    }
}




