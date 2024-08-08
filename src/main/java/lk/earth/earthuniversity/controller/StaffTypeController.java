package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.StaffTypeDao;
import lk.earth.earthuniversity.entity.StaffType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/staffTypes")
public class StaffTypeController {

    @Autowired
    private StaffTypeDao staffTypeDao;

    @GetMapping(path ="/list",produces = "application/json")
    public List<StaffType> get() {

        List<StaffType> types = this.staffTypeDao.findAll();

        types = types.stream().map(
                type -> { StaffType s = new StaffType();
                            s.setId(type.getId());
                            s.setType(type.getType());
                            return s; }
        ).collect(Collectors.toList());

        return types;

    }

}


