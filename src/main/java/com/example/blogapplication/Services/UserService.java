package com.example.blogapplication.Services;

import com.example.blogapplication.Entities.User;

public interface UserService {

    User getUserDetailsById(User user,String id);
    boolean deleteUserById(User user,String id);
    User updateUserDetailyById(User user,String id);
}
