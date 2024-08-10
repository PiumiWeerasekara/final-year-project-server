package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.DoctorDao;
import lk.earth.earthuniversity.dao.User1Dao;
import lk.earth.earthuniversity.entity.*;
import lk.earth.earthuniversity.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private User1Dao userdao;
    @Autowired
    private DoctorDao doctorDao;

    @GetMapping(produces = "application/json")
    public List<Appointment> get(@RequestParam HashMap<String, String> params) {

        List<Appointment> appointments = this.appointmentDao.findAll();

        if (params.isEmpty()) return appointments;

        String name = params.get("name");
        String nic = params.get("nic");
        String doctorId = params.get("docId");
        String appointmentNo = params.get("appointmentNo");
        Stream<Appointment> estream = appointments.stream();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date appointmentDate = null;
        try {
            appointmentDate = sdf.parse(params.get("appointmentDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (nic != null) estream = estream.filter(e -> e.getPatient().getNic().contains(nic));
        if (name != null)
            estream = estream.filter(e -> e.getPatient().getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getPatient().getLastName().toLowerCase().contains(name.toLowerCase()));
        if (doctorId != null)
            estream = estream.filter(e -> e.getSchedule().getDoctor().getId() == Integer.parseInt(doctorId));
        if (appointmentDate != null) {
            Date finalScheduleDate = appointmentDate;
            estream = estream.filter(e -> e.getAppointmentDate().equals(finalScheduleDate));
        }
        if (appointmentNo != null)
            estream = estream.filter(e -> e.getAppointmentNo() == (Integer.parseInt(appointmentNo)));

        return estream.collect(Collectors.toList());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> save(@RequestBody Appointment appointment) {

        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        if (errors.isEmpty()) {
            appointmentDao.save(appointment);

            String to = appointment.getPatient().getEmail();
            String subject = "Appointment Confirmation";
            String text = "<html>" +
                    "<body>" +
                    "<div>" +
                    "<img src='cid:logo' alt='Logo' style='width:150px; height:auto;' align='middle'/>" + // Add your logo URL
                    "<h2 style='color:black;'>Your appointment has been successfully scheduled.</h2>" +
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
                    "<h3 style='color:black;'>Doctor Details</h3>" +
                    "<p>" +
                    "<b style='color:black;'>Name                         :</b> " + appointment.getSchedule().getDoctor().getTitle() + " " + appointment.getSchedule().getDoctor().getFirstName() + " " + appointment.getSchedule().getDoctor().getLastName() + "<br/>" +
                    "<b style='color:black;'>Specialization               :</b> " + appointment.getSchedule().getDoctor().getSpeciality().getName() + "<br/>" + // Ensure 'getSpecialization()' is a valid method
                    "</p>" +
                    "<p style='color:black;'>Thank you.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            emailService.sendEmail(to, subject, text);

        } else {
            errors = "Server Validation Errors : <br> " + errors;
        }

        response.put("id", String.valueOf(appointment.getId()));
        response.put("url", "/doctor/" + appointment.getId());
        response.put("errors", errors);

        return response;
    }

    @GetMapping(path = "/number", produces = "text/plain")
    public int get(int scheduleId) {

        String lastAppointmentNumber = appointmentDao.findLastAppointmentNumber(scheduleId);

        if (lastAppointmentNumber == null || lastAppointmentNumber.isEmpty()) {
            return 1;
        }

        int nextAppointmentNumber;
        try {
            nextAppointmentNumber = Integer.parseInt(lastAppointmentNumber) + 1;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid appointment number format: " + lastAppointmentNumber, e);
        }

        return nextAppointmentNumber;
    }

    @GetMapping(path = "/availableSchedules", produces = "application/json")
    public List<AppointmentSearch> getAvailableAppointments(@RequestParam HashMap<String, String> params) {

        List<Object[]> results = this.appointmentDao.findAllAvailable();
        List<AppointmentSearch> appointments = results.stream()
                .map(e -> new AppointmentSearch(
                        e[0].toString(), // scheduleDate
                        e[1].toString(), // startTime
                        e[2].toString(), // appointmentNo
                        ((Number) e[3]).intValue(),//scheduleId
                        ((Number) e[4]).intValue(), // doctorId
                        ((Number) e[5]).intValue(), // specialityId
                        ((Number) e[6]).intValue() // specialityId
                ))
                .collect(Collectors.toList());

        if (params.isEmpty()) {
            return appointments;
        }

        String doctorId = params.get("doctorId");
        String scheduleDate = params.get("scheduleDate");
        String specialityId = params.get("specialityId");
        Stream<AppointmentSearch> estream = appointments.stream();

        if (doctorId != null) {
            int docId = Integer.parseInt(doctorId);
            estream = estream.filter(e -> e.getDoctorId() == docId);
        }
        if (scheduleDate != null) {
            estream = estream.filter(e -> e.getScheduleDate().equals(scheduleDate));
        }
        if (specialityId != null) {
            int specId = Integer.parseInt(specialityId);
            estream = estream.filter(e -> e.getSpecialityId() == specId);
        }

        return estream.collect(Collectors.toList());
    }

    @GetMapping(path = "/lastAppointment", produces = "application/json")
    public Appointment get(@RequestParam("id") Integer scheduleId) {
        List<Appointment> appointments = this.appointmentDao.findLastAppointment(scheduleId);
        return appointments.isEmpty() ? null : appointments.get(0);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> cancel(@RequestBody Appointment appointment) {

        HashMap<String, String> responce = new HashMap<>();
        String errors = "";

        try {
            String to = appointment.getPatient().getEmail();
            String subject = "Appointment Cancellation";
            String text = "<html>" +
                    "<body>" +
                    "<div>" +
                    "<img src='cid:logo' alt='Logo' style='width:150px; height:auto;' align='middle'/>" +
                    "<h2 style='color:black;'>Your appointment has been cancelled.</h2>" +
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
            appointmentDao.updateStatus(appointment.getId());

        } catch (Exception e) {
            errors = "Errors occur when cancelling the schedule";
        }
        responce.put("id", String.valueOf(appointment.getId()));
        responce.put("url", "/doctor/" + appointment.getId());
        responce.put("errors", errors);

        return responce;
    }

    @GetMapping(value = "/currentAppointments", produces = "application/json")
    public List<Appointment> getMyCurrentScheduleAppointments(@RequestParam HashMap<String, String> params) {

        String userName = params.get("username");

        int staffId = userdao.findByUsername(userName).getStaff().getId();
        int docId = doctorDao.findByStaffId(staffId).getId();

        List<Appointment> appointments = this.appointmentDao.findMyCurrentScheduleAppointments(docId);

        if (params.isEmpty()) return appointments;

        String name = params.get("name");
        String nic = params.get("nic");
        String appointmentNo = params.get("appointmentNo");
        Stream<Appointment> estream = appointments.stream();

        if (nic != null) estream = estream.filter(e -> e.getPatient().getNic().contains(nic));
        if (name != null)
            estream = estream.filter(e -> e.getPatient().getFirstName().toLowerCase().contains(name.toLowerCase()) || e.getPatient().getLastName().toLowerCase().contains(name.toLowerCase()));
        if (appointmentNo != null)
            estream = estream.filter(e -> e.getAppointmentNo() == (Integer.parseInt(appointmentNo)));

        return estream.collect(Collectors.toList());

    }

    //dashboad details
    @GetMapping(value = "/getAllOnGoingSchedules", produces = "application/json")
    public List<Appointment> getAllOnGoingSchedules() {
        List<Appointment> ongoingAppointments = appointmentDao.getAllOnGoingSchedules();

//        List<Appointment> combinedAppointments = new List<Appointment>() {
//        };

        // Add all ongoing appointments to the result list
//        combinedAppointments.addAll(ongoingAppointments);

        // Step 2: Loop through each ongoing appointment and fetch the next appointment
//        for (Appointment ongoingAppointment : ongoingAppointments) {
//            List<Appointment> nextAppointments = appointmentDao.getNext(ongoingAppointment.getSchedule().getId());
//            if (!nextAppointments.isEmpty()) {
//
//                // Step 3: Create and populate the result map
//                Map<String, Object> resultMap = new HashMap<>();
//                resultMap.put("ongoingAppointments", ongoingAppointments);
//                resultMap.put("nextAppointmentNo", nextAppointmentNo);
//
//                return resultMap;
//            }
//        }

//        return combinedAppointments;
        return ongoingAppointments;

    }

    @GetMapping(value = "/findMyUpcomingScheduleAppointments", produces = "application/json")
    public List<Appointment> findMyUpcomingScheduleAppointments(@RequestParam HashMap<String, String> params) {

        String userName = params.get("username");

        int staffId = userdao.findByUsername(userName).getStaff().getId();
        int docId = doctorDao.findByStaffId(staffId).getId();

        List<Appointment> appointments = this.appointmentDao.findMyUpcomingScheduleAppointments(docId);

        return appointments;

    }

    @GetMapping(path = "/getVisitCountForCurrentYear/{id}", produces = "application/json")
    public List<Object[]> getVisitCountForCurrentYear(@PathVariable Integer id) {
        List<Object[]> obj = this.appointmentDao.getVisitCountForCurrentYear(id);
        return obj;

    }

}




