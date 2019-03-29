package com.aoji.controller;

import com.aoji.service.StudentCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StudentCostController extends  BaseController{

    @Autowired
    private StudentCostService studentCostService;


}
