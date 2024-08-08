package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.ScheduleDao;
import lk.earth.earthuniversity.dao.StaffDao;
import lk.earth.earthuniversity.entity.Schedule;
import lk.earth.earthuniversity.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/staff")
public class StaffController {

    @Autowired
    private StaffDao staffDao;
    @Autowired
    private ScheduleDao scheduleDao;

    @GetMapping(produces = "application/json")
    public List<Staff> get(@RequestParam HashMap<String, String> params) {

        List<Staff> sta = this.staffDao.findAll();

        if (params.isEmpty()) return sta;

        String name = params.get("name");
        String nic = params.get("nic");
        String staffTypeId = params.get("staffTypeId");
        Stream<Staff> estream = sta.stream();

        if (staffTypeId != null)
            estream = estream.filter(e -> e.getStaffType().getId() == Integer.parseInt(staffTypeId));
        if (nic != null) estream = estream.filter(e -> e.getNic().contains(nic));
        if (name != null)
            estream = estream.filter(e -> e.getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getLastName().toLowerCase().contains(name.toLowerCase()));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Staff> get() {

        List<Staff> staffs = this.staffDao.findAllNameId();

        staffs = staffs.stream().map(
                staff -> {
                    Staff s = new Staff(staff.getId(), staff.getTitle(), staff.getFirstName(), staff.getLastName(), staff.getPhoto(), staff.getDob(), staff.getNic(), staff.getAddress(), staff.getContactNo(), staff.getEmail(), staff.getGender(), staff.getStaffType(), staff.getStatus());
                    return s;
                }
        ).collect(Collectors.toList());

        return staffs;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String, String> save(@RequestBody Staff staff) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Staff doc = staffDao.findByNic(staff.getNic());

//        if(emp1!=null && employee.getId()!=emp1.getId())
//            errors = errors+"<br> Existing Number";
        if (doc != null && staff.getId() != doc.getId())
            errors = errors + "<br> Existing NIC";

        if (errors == "") staffDao.save(staff);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(staff.getId()));
        responce.put("url", "/staff/" + staff.getId());
        responce.put("errors", errors);

        return responce;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> inactive(@PathVariable Integer id) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        Staff staff = staffDao.findByMyId(id);

        //List<Staff> sch = scheduleDao.findUpComingSchedulesByDoctorID(id);

        if (staff == null)
            errors = errors + "<br> Staff Does Not Existed";

//        else if(staff.size()>0)
//            errors = errors+"Doctor has upcoming Schedules";

        if (errors == "") staffDao.updateStatusAsinactive(id);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(id));
        responce.put("url", "/staff/" + id);
        responce.put("errors", errors);

        return responce;
    }
}




