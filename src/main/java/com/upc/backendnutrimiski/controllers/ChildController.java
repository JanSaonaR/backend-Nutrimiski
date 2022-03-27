package com.upc.backendnutrimiski.controllers;

import com.upc.backendnutrimiski.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/child")
public class ChildController {

    @Autowired
    ChildService childService;

}
