package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.ScheduleDao;
import lk.earth.earthuniversity.entity.Appointment;
import lk.earth.earthuniversity.entity.Schedule;
import lk.earth.earthuniversity.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private EmailService emailService;

    @GetMapping(produces = "application/json")
    public List<Schedule> get(@RequestParam HashMap<String, String> params) {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        if (params.isEmpty()) return schedules;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date scheduleDate = null;
        try {
            scheduleDate = sdf.parse(params.get("scheduleDate")); // Convert string to Date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String doctorId = params.get("doctorId");
        String roomId = params.get("roomId");
        Stream<Schedule> estream = schedules.stream();

        if (doctorId != null) estream = estream.filter(e -> e.getDoctor().getId() == Integer.parseInt(doctorId));
        if (scheduleDate != null) {
            Date finalScheduleDate = scheduleDate;
            estream = estream.filter(e -> e.getScheduleDate().equals(finalScheduleDate));
        }
        if (roomId != null) estream = estream.filter(e -> e.getRoom().getId() == Integer.parseInt(roomId));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Schedule> get() {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        schedules = schedules.stream().map(
                schedule -> {
                    Schedule d = new Schedule(schedule.getId(), schedule.getDoctor(), schedule.getScheduleDate(), schedule.getRoom(), schedule.getNoOfPatient());
                    return d;
                }
        ).collect(Collectors.toList());

        return schedules;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String, String> save(@RequestBody Schedule schedule) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";
        List<Schedule> overlappingSchedules = scheduleDao.checkDoctorAvailability(schedule.getDoctor().getId(), schedule.getScheduleDate(), schedule.getStartTime(), schedule.getEndTime());
        if (!overlappingSchedules.isEmpty()) {
            errors = "Doctor is not available";
        }
        if (errors == "")
            scheduleDao.save(schedule);
        else errors = "Server Validation Errors : <br> " + errors;

        responce.put("id", String.valueOf(schedule.getId()));
        responce.put("url", "/schedule/" + schedule.getId());
        responce.put("errors", errors);

        return responce;
    }

    @GetMapping(path = "/availableSchedules", produces = "application/json")
    public List<Schedule> getAvailableSchedules(@RequestParam HashMap<String, String> params) {

        List<Schedule> schedules = this.scheduleDao.findAllNameId();

        if (params.isEmpty()) return schedules;

        String doctorId = params.get("doctorId");
        String scheduleDate = params.get("scheduleDate");
        String specialityId = params.get("specialityId");
        Stream<Schedule> estream = schedules.stream();

        if (doctorId != null) estream = estream.filter(e -> e.getDoctor().getId() == Integer.parseInt(doctorId));
        if (scheduleDate != null) estream = estream.filter(e -> e.getScheduleDate().equals(scheduleDate));
        if (specialityId != null)
            estream = estream.filter(e -> e.getDoctor().getSpeciality().getId() == Integer.parseInt(specialityId));

        return estream.collect(Collectors.toList());

    }

    @PostMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> cancel(@PathVariable Integer id) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        List<Appointment> appointments = appointmentDao.findLastAppointment(id);

        int size = appointments.size();
        if (appointments != null && !appointments.isEmpty() && size > 0) {

            for (int i = 0; i < size; i++) {
                Appointment appointment = appointments.get(i);
                try {
                    String to = appointment.getPatient().getEmail();
                    String subject = "Appointment Cancellation";
                    String text = "<html>" +
                            "<body>" +
                            "<div>" +
                            "<img src='cid:logo' alt='Logo' style='width:150px; height:auto;' align='middle'/>" +
                            "<h2 style='color:black;'>We regret to inform you that your appointment has been cancelled. We apologize for any inconvenience caused.</h2>" +
                            "<h3 style='color:black;'>Channel Details</h3>" +
                            "<p>" +
                            "<b style='color:black;'>Appointment No               :</b> " + appointment.getAppointmentNo() + "<br/>" +
                            "<b style='color:black;'>Appointment Date             :</b> " + appointment.getAppointmentDate() + "<br/>" +
                            "<b style='color:black;'>Time                         :</b> " + appointment.getStartTime() + "<br/>" +
                            "</p>" +
                            "<h3 style='color:black;'>Patient Details</h3>" +
                            "<p>" +
                            "<b style='color:black;'>Name                         :</b> " + appointment.getPatient().getTitle() + " " + appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() + "<br/>" +
                            "<b style='color:black;'>NIC Number                   :</b> " + appointment.getPatient().getNic() + "<br/>" +
                            "</p>" +
                            "<p style='color:black;'>Thank you.</p>" +
                            "</div>" +
                            "</body>" +
                            "</html>";

                    emailService.sendEmail(to, subject, text);
                    if (i == size - 1) {
                        scheduleDao.updateStatus(id);
                    }

                } catch (Exception e) {
                    errors = "Errors occur when cancelling the schedule";
                }
            }

        } else {
            scheduleDao.updateStatus(id);
        }
        responce.put("id", String.valueOf(id));
        responce.put("url", "/doctor/" + id);
        responce.put("errors", errors);

        return responce;
    }

    @GetMapping(path = "/byScheduleId", produces = "application/json")
    public Optional<Schedule> get(@RequestParam("id") Integer id) {
        Schedule schedule = scheduleDao.findByMyId(id);
        return Optional.ofNullable(schedule);
    }
}




