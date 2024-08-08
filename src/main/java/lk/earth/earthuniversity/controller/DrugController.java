package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.DoctorDao;
import lk.earth.earthuniversity.dao.DrugDao;
import lk.earth.earthuniversity.entity.Doctor;
import lk.earth.earthuniversity.entity.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugDao drugDao;

    @GetMapping(path ="/list", produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")
    public List<Drug> get(@RequestParam HashMap<String, String> params) {

        List<Drug> drugs = this.drugDao.findAllNameId();

        drugs = drugs.stream().map(
                drug -> {
                    Drug d = new Drug(drug.getId(), drug.getName());
                    return  d;
                }
        ).collect(Collectors.toList());

        return drugs;

    }
}




