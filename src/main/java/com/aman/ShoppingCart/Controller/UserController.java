package com.aman.ShoppingCart.Controller;

import com.aman.ShoppingCart.Dto.UserDto;
import com.aman.ShoppingCart.Exception.AlreadyExistException;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Request.CreateUserRequest;
import com.aman.ShoppingCart.Request.UserUpdateRequest;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Service.UserService;
import com.aman.ShoppingCart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getUserNyId(@PathVariable Long id){

        try{
            User user= userService.getUserById(id);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new APIResponse("Success",userDto));

        }
        catch (ResolutionException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
     }
    @PostMapping("user")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request){
        try{
            User user=userService.createUser(request);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new APIResponse("Created",userDto));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(),null));
        }

     }

     @PutMapping("update/{userId}")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable  Long userId){
        try{
            User user=userService.updateUser(request,userId);
            UserDto userDto=userService.convertToDto(user);
            return ResponseEntity.ok(new APIResponse("Updated",userDto));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));

        }
     }

     @DeleteMapping("delete/{UserId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("Deleted",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));

        }
    }
}
