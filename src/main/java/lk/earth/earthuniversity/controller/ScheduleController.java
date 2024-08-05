package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.ScheduleDao;
import lk.earth.earthuniversity.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleDao scheduleDao;

    @GetMapping(produces = "application/json")
    //@PreAuthorize("hasAuthority('employee-select')")
    public List<Schedule> get(@RequestParam HashMap<String, String> params) {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        if(params.isEmpty())  return schedules;

        String doctorId= params.get("doctorId");
        String scheduleDate= params.get("scheduleDate");
        String roomId= params.get("roomId");
        Stream<Schedule> estream = schedules.stream();

        if(doctorId!=null) estream = estream.filter(e -> e.getDoctor().getId()==Integer.parseInt(doctorId));
        if(scheduleDate!=null) estream = estream.filter(e -> e.getScheduleDate().equals(scheduleDate));
        if(roomId!=null) estream = estream.filter(e -> e.getRoom().getId()==Integer.parseInt(roomId));

        return estream.collect(Collectors.toList());

    }
    @GetMapping(path ="/list",produces = "application/json")
    public List<Schedule> get() {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        schedules = schedules.stream().map(
                schedule -> {
                    Schedule d = new Schedule(schedule.getId(), schedule.getDoctor(), schedule.getScheduleDate(), schedule.getRoom());
                    return  d;
                }
        ).collect(Collectors.toList());

        return schedules;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> save(@RequestBody Schedule schedule){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        //Doctor doc = doctorScheduleDao.findByNic(doctor.getNic());

//        if(emp1!=null && employee.getId()!=emp1.getId())
//            errors = errors+"<br> Existing Number";
//        if(doc!=null && doctor.getId()!=doc.getId())
//            errors = errors+"<br> Existing NIC";

//        if(errors=="")
            scheduleDao.save(schedule);
        //else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(schedule.getId()));
        responce.put("url","/schedule/"+schedule.getId());
        responce.put("errors",errors);

        return responce;
    }

    @GetMapping(path = "/availableSchedules", produces = "application/json")
    //@PreAuthorize("hasAuthority('employee-select')")
    public List<Schedule> getAvailableSchedules(@RequestParam HashMap<String, String> params) {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        if(params.isEmpty())  return schedules;

        String doctorId= params.get("doctorId");
        String scheduleDate= params.get("scheduleDate");
        String specialityId= params.get("specialityId");
        Stream<Schedule> estream = schedules.stream();

        if(doctorId!=null) estream = estream.filter(e -> e.getDoctor().getId()==Integer.parseInt(doctorId));
        if(scheduleDate!=null) estream = estream.filter(e -> e.getScheduleDate().equals(scheduleDate));
        if(specialityId!=null) estream = estream.filter(e -> e.getDoctor().getSpeciality().getId()==Integer.parseInt(specialityId));

        return estream.collect(Collectors.toList());

    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public HashMap<String,String> delete(@PathVariable Integer id){
//
//        HashMap<String,String> responce = new HashMap<>();
//        String errors="";
//
//        Doctor doc = doctorDao.findByMyId(id);
//
//        if(doc==null)
//            errors = errors+"<br> Doctor Does Not Existed";
//
//        if(errors=="") doctorDao.delete(doc);
//        else errors = "Server Validation Errors : <br> "+errors;
//
//        responce.put("id",String.valueOf(id));
//        responce.put("url","/doctor/"+id);
//        responce.put("errors",errors);
//
//        return responce;
//    }

    @GetMapping(path ="/byScheduleId",produces = "application/json")
    public Optional<Schedule> get(@RequestParam("id") Integer id) {

        Schedule schedule = scheduleDao.findByMyId(id);

        return Optional.ofNullable(schedule);

    }
}




