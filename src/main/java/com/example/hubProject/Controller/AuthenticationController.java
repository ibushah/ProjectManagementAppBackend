package com.example.hubProject.Controller;

import com.example.hubProject.Commons.ApiResponse;
import com.example.hubProject.Commons.AuthToken;
import com.example.hubProject.Config.JwtTokenUtil;
import com.example.hubProject.DTO.UserEmailDTO;
import com.example.hubProject.DTO.LoginUser;
import com.example.hubProject.DTO.UserDto;
import com.example.hubProject.Model.User;
import com.example.hubProject.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);

        return new ApiResponse<>(200, "success",new AuthToken(token,user.getName(),user.getUserType()));
    }


    @PostMapping("/user")
    public ApiResponse<User> saveUser(@RequestBody UserDto user){

        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userService.save(user));
    }

    @GetMapping("/allusers")
    public List<User> findAll()
    {
        return userService.findAll();
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword(@RequestBody UserEmailDTO userEmailDTO)
    { return   userService.forgotPassword(userEmailDTO); }

    @PostMapping("/newuser")
    public String postNewUser(@RequestBody UserEmailDTO userEmailDTO)
    { return userService.postNewUser(userEmailDTO);}

    @DeleteMapping("/deleteuser/{id}")
    public void delete(@PathVariable("id") Long id)
    {
         userService.delete(id);
    }

    @GetMapping("/getuser/{id}")
    public User getUser(@PathVariable("id") Long id)
    {
       return userService.findById(id);
    }

    @PutMapping("/updateuser/{id}")
    public String updateUser(@PathVariable("id") Long id,@RequestBody UserDto userDto)
    {
        return userService.updateUser(id,userDto);
    }


}
