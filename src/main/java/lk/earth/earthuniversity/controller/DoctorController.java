package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.DoctorDao;
import lk.earth.earthuniversity.dao.EmployeeDao;
import lk.earth.earthuniversity.dao.ScheduleDao;
import lk.earth.earthuniversity.dao.StaffDao;
import lk.earth.earthuniversity.entity.*;
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
    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private StaffDao staffDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Doctor> get(@RequestParam HashMap<String, String> params) {

        List<Doctor> doctors = this.doctorDao.findAll();

        if (params.isEmpty()) return doctors;

        String name = params.get("name");
        String nic = params.get("nic");
        String specialityId = params.get("specialityId");
        Stream<Doctor> estream = doctors.stream();

        if (specialityId != null)
            estream = estream.filter(e -> e.getSpeciality().getId() == Integer.parseInt(specialityId));
        if (nic != null) estream = estream.filter(e -> e.getNic().contains(nic));
        if (name != null)
            estream = estream.filter(e -> e.getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getLastName().toLowerCase().contains(name.toLowerCase()));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Doctor> get() {

        List<Doctor> doctors = this.doctorDao.findAllNameId();

        doctors = doctors.stream().map(doctor -> {
            Doctor d = new Doctor(doctor.getId(), doctor.getTitle(), doctor.getFirstName(), doctor.getLastName(), doctor.getPhoto(), doctor.getDob(), doctor.getNic(), doctor.getAddress(), doctor.getContactNo(), doctor.getEmail(), doctor.getGender(), doctor.getSpeciality(), doctor.getMedicalLicenseNo(), doctor.getStatus(), doctor.getLicenseEXPDate(), doctor.getStaff(), doctor.getFee());
            return d;
        }).collect(Collectors.toList());

        return doctors;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String, String> save(@RequestBody Doctor doctor) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Doctor doc = doctorDao.findByNic(doctor.getNic());

//        if(emp1!=null && employee.getId()!=emp1.getId())
//            errors = errors+"<br> Existing Number";
        if (doc != null && doctor.getId() != doc.getId()) errors = errors + "<br> Existing NIC";

        if (errors == "") {
            StaffType st = new StaffType();
            st.setId(14);
            st.setType("Doctor");
            Staff staff = new Staff();
            staff.setTitle(doctor.getTitle());
            staff.setFirstName(doctor.getFirstName());
            staff.setLastName(doctor.getLastName());
            staff.setPhoto(doctor.getPhoto());
            staff.setDob(doctor.getDob());
            staff.setNic(doctor.getNic());
            staff.setAddress(doctor.getAddress());
            staff.setContactNo(doctor.getContactNo());
            staff.setEmail(doctor.getEmail());
            staff.setAddress(doctor.getAddress());
            staff.setGender(doctor.getGender());
            staff.setStaffType(st);
            staff.setStatus(doctor.getStatus());

            staffDao.save(staff);
            doctor.setStaff(staff);
            doctorDao.save(doctor);

        } else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(doctor.getId()));
        responce.put("url", "/doctor/" + doctor.getId());
        responce.put("errors", errors);

        return responce;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> delete(@PathVariable Integer id) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Doctor doc = doctorDao.findByMyId(id);

        if (doc == null) errors = errors + "<br> Doctor Does Not Existed";

        if (errors == "") doctorDao.delete(doc);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(id));
        responce.put("url", "/doctor/" + id);
        responce.put("errors", errors);

        return responce;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> inactive(@PathVariable Integer id) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Doctor doc = doctorDao.findByMyId(id);

        List<Schedule> sch = scheduleDao.findUpComingSchedulesByDoctorID(id);

        if (doc == null) errors = errors + "<br> Doctor Does Not Existed";

        else if (sch.size() > 0) errors = errors + "Doctor has upcoming Schedules";

        if (errors == "") doctorDao.updateStatusAsinactive(id);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(id));
        responce.put("url", "/doctor/" + id);
        responce.put("errors", errors);

        return responce;
    }
}




