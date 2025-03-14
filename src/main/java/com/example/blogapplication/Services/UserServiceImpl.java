package com.example.blogapplication.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.UserNotFoundException;
import com.example.blogapplication.Repositories.BlogRepository;
import com.example.blogapplication.Repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BlogRepository blogRepository;

    public UserServiceImpl(UserRepository userRepository,BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public User getUserDetailsById(User user, String id) {
        String userId = user.getId();
        if (!userId.equals(id)) {
            throw new UserNotFoundException("you can not access user info");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    @Override
    public boolean deleteUserById(User user, String id) {
        
        String userId = user.getId();
        if(!userId.equals(id)){
            return false;
        }

        User userToDelete = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));


        blogRepository.deleteByUserId(userToDelete.getId());
        // blogRepository.de

        userRepository.delete(userToDelete);

        return true;
    }

    @Override
    public User updateUserDetailyById(User user, String id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'updateUserDetailyById'");
    }

}
