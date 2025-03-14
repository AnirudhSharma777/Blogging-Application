package com.example.blogapplication.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapplication.Entities.Blog;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.AccessDeniedException;
import com.example.blogapplication.ResponseDto.UserResponseDto;
import com.example.blogapplication.Services.BlogServiceImpl;
import com.example.blogapplication.Services.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserServiceImpl userService;

    private BlogServiceImpl blogService;

    public UserController(UserServiceImpl userService, BlogServiceImpl blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetailsById(@PathVariable(name = "id") String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            User currentUser = (User) authentication.getPrincipal();
            User userEntity = userService.getUserDetailsById(currentUser, id);
            List<Blog> blogs = blogService.findByUserId(currentUser.getId());
            userEntity.getBlogs().clear();
            userEntity.getBlogs().addAll(blogs);
            UserResponseDto response = UserResponseDto.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getName())
                    .email(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .blogs(userEntity.getBlogs())
                    .token(userEntity.getToken())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid user ID provided", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            
            User currentUser = (User) authentication.getPrincipal();
            System.out.println("id :"+id);
            System.out.println("currentUser id :"+currentUser.getId());
            boolean isDeleted = userService.deleteUserById(currentUser, id);

            if (isDeleted) {
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found or deletion not permitted", HttpStatus.NOT_FOUND);
            }

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid user ID provided", HttpStatus.BAD_REQUEST);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>("You do not have permission to delete this user", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/blogs")
    public ResponseEntity<?> getListOfAllBlogs() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            User currentUser = (User) authentication.getPrincipal();

            List<Blog> blogs = blogService.findByUserId(currentUser.getId());
            currentUser.getBlogs().clear();
            currentUser.getBlogs().addAll(blogs);

            return new ResponseEntity<>(blogs, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred while fetching blogs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
