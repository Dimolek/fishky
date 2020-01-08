package com.fishky.controller;

import com.fishky.dto.abstracts.IdDto;
import com.fishky.dto.user.UserCreateDto;
import com.fishky.dto.user.UserDto;
import com.fishky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @RequestMapping(method = RequestMethod.POST, value = "/addUser")
    public IdDto add(@RequestBody UserCreateDto user) {
        return service.add(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUserById")
    public UserDto find(@RequestParam(value="id") String id) {
        return service.read(IdDto.of(id));
    }
}