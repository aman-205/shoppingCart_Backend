package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.UserDto;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.UserRepo;
import com.aman.ShoppingCart.Request.CreateUserRequest;
import com.aman.ShoppingCart.Request.UserUpdateRequest;
import com.aman.ShoppingCart.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No user found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepo.existsByEmail(request.getEmail()))
                .map(req->{
                    User user= new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepo.save(user);
                }).orElseThrow(()-> new ResourceNotFoundException("User Already Exist"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {

        return userRepo.findById(userId).map(exUser->{
            exUser.setFirstName(request.getFirstName());
            exUser.setLastName(request.getLastName());
            return userRepo.save(exUser);
        }).orElseThrow(()->new ResourceNotFoundException("User not found"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{
            throw  new ResourceNotFoundException("User not found");
        });
    }
    @Override
    public UserDto convertToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
