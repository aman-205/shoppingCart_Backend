package com.aman.ShoppingCart.Security;

import com.aman.ShoppingCart.Repo.UserRepo;
import com.aman.ShoppingCart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepo.findByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return UserDetail.buildUserDetails(user);
    }
}
