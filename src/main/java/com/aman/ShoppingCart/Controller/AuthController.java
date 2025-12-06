package com.aman.ShoppingCart.Controller;


import com.aman.ShoppingCart.Config.JwtUtils;
import com.aman.ShoppingCart.Request.LoginRequest;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Response.JWTResponse;
import com.aman.ShoppingCart.Security.UserDetail;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<APIResponse> login(@Valid @RequestBody LoginRequest request){
       try{
           Authentication authentication= authenticationManager
                   .authenticate(new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword()));
           SecurityContextHolder.getContext().setAuthentication(authentication);
           String jwt= jwtUtils.generateToken(authentication);
           UserDetail userDetail=(UserDetail) authentication.getPrincipal();
           JWTResponse jwtResponse= new JWTResponse(userDetail.getId(),jwt);
           return ResponseEntity.ok(new APIResponse("Login Success", jwtResponse));

       } catch (AuthenticationException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse(e.getMessage(),null));
       }
    }
}
