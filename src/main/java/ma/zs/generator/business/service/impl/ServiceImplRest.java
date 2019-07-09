package ma.zs.generator.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/generator/serviceimpl/"})
public class ServiceImplRest {

    @Autowired
    ServiceImplEngine serviceEngine;

    @PostMapping("/")
    public void generate() throws Exception {
        serviceEngine.generate();
    }
}
