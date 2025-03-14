package com.example.blogapplication.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapplication.Dto.BlogDto;
import com.example.blogapplication.Entities.Blog;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.ResponseDto.BlogResponseDto;
import com.example.blogapplication.Services.BlogServiceImpl;
import com.example.blogapplication.Utils.ServiceUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

    private BlogServiceImpl blogService;

    private ServiceUtil serviceUtil;;

    public BlogController(BlogServiceImpl blogService, ServiceUtil serviceUtil) {
        this.blogService = blogService;
        this.serviceUtil = serviceUtil;
    }

    @PostMapping
    public ResponseEntity<?> createNewBlog(@Valid @RequestBody BlogDto blog) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            User currentUser = (User) authentication.getPrincipal();

            blog.setAuthor(currentUser.getName());

            Blog blogEntity = blogService.createBlog(blog,currentUser);

            BlogResponseDto response = BlogResponseDto.builder()
            .id(blogEntity.getId())
            .title(blogEntity.getTitle())
            .content(blogEntity.getContent())
            .author(blogEntity.getAuthor())
            .userId(blogEntity.getUserId())
            .createdAt(blogEntity.getCreatedAt())
            .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // System.err.println("Error creating blog: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while creating the blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable(name = "id") String id) {
        try {
            Blog blog = blogService.getBlogById(id);

            if (blog == null) {
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }
            

            BlogResponseDto response = BlogResponseDto.builder()
                    .id(blog.getId())
                    .title(blog.getTitle())
                    .content(blog.getContent())
                    .author(blog.getAuthor())
                    .userId(blog.getUserId())
                    .createdAt(blog.getCreatedAt())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // System.err.println("Error fetching blog: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while fetching the blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable(name = "id") String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            User currentUser = (User) authentication.getPrincipal();

            Blog blog = blogService.getBlogById(id);
            // System.out.println(blog.getId());
            if (blog == null) {
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }

            if (!blog.getAuthor().equals(currentUser.getName())) {
                return new ResponseEntity<>("You are not authorized to delete this blog", HttpStatus.FORBIDDEN);
            }

            blogService.deleteBlogById(id);

            return new ResponseEntity<>("Blog deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable(name = "id") String id, @Valid @RequestBody BlogDto blogDto) {
        try {
            // Get the authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || !(authentication.getPrincipal() instanceof User)) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }

            User currentUser = (User) authentication.getPrincipal();

            Blog existingBlog = blogService.getBlogById(id);
            if (existingBlog == null) {
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }

            if (!existingBlog.getAuthor().equals(currentUser.getName())) {
                return new ResponseEntity<>("You are not authorized to update this blog", HttpStatus.FORBIDDEN);
            }

            existingBlog.setTitle(blogDto.getTitle());
            existingBlog.setContent(blogDto.getContent());
            existingBlog.setUpdatedAt(serviceUtil.convertDateToString());

            Blog updatedBlog = blogService.updateBlog(existingBlog);

            BlogResponseDto response = BlogResponseDto.builder()
                    .id(updatedBlog.getId())
                    .title(updatedBlog.getTitle())
                    .content(updatedBlog.getContent())
                    .author(updatedBlog.getAuthor())
                    .userId(updatedBlog.getUserId())
                    .createdAt(updatedBlog.getCreatedAt())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error updating blog: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while updating the blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getListOfBlogs(){
        List<Blog> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs,HttpStatus.OK);
    }

}
