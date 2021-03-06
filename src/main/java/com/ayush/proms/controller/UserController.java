package com.ayush.proms.controller;

import com.ayush.proms.model.User;
import com.ayush.proms.pojos.UserMinimalDetail;
import com.ayush.proms.pojos.UserPOJO;
import com.ayush.proms.service.UserService;
import com.ayush.proms.utils.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/import-users")
    public ResponseEntity importUsers(@RequestBody MultipartFile file) throws IOException {
        Integer data = userService.importUser(file);
        if (data>=1){
            return new ResponseEntity(successResponse("Users imported Successfully",data), HttpStatus.OK);
        }else {
            return new ResponseEntity(errorResponse("Failed to import Users",data),HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@Valid @RequestBody UserPOJO userPOJO){
        Long data = userService.createUser(userPOJO);
        if (data>=1){
            return new ResponseEntity(successResponse("Users created Successfully",data), HttpStatus.OK);
        }else {
            return new ResponseEntity(errorResponse("Failed to create User",data),HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity allUsers()  {
        List<User> data = userService.getAllUsers();
        if (data!=null){
            return new ResponseEntity(successResponse("Users fetched Successfully",data), HttpStatus.OK);
        }else {
            return new ResponseEntity(errorResponse("Users fetched Successfully",null),HttpStatus.OK);
        }
    }

    @GetMapping("/current-user-friends")
    public ResponseEntity getCurrentUserFriends()  {
        List<UserMinimalDetail> data = userService.getCurrentUserFriends();
        System.out.println(data.get(0).getId());
        System.out.println(data.get(0).getName());
        if (data!=null){
            return new ResponseEntity(successResponse("Users fetched Successfully",data), HttpStatus.OK);
        }else {
            return new ResponseEntity(errorResponse("Users fetched Successfully",null),HttpStatus.OK);
        }
    }
}
