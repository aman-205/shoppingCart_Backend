package com.aman.ShoppingCart.Service;


import com.aman.ShoppingCart.Dto.UserDto;
import com.aman.ShoppingCart.Request.CreateUserRequest;
import com.aman.ShoppingCart.Request.UserUpdateRequest;
import com.aman.ShoppingCart.model.User;

public interface UserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
