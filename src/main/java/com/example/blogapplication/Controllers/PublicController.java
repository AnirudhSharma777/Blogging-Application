package com.example.blogapplication.Controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapplication.Entities.Blog;
import com.example.blogapplication.Services.BlogServiceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final BlogServiceImpl blogService;

    public PublicController(BlogServiceImpl blogService) {
        this.blogService = blogService;
    }


    @GetMapping("/blogs/pagination/{page}/{size}")
    public ResponseEntity<?> getListAccordingToPagination(
       @PathVariable(name="page") int page,
       @PathVariable(name="size") int size
    ) {
        try {
            Page<Blog> blog = blogService.findBlogByPagination(page, size);
            return ResponseEntity.ok(blog);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching paginated blogs: " + e.getMessage());
        }
    }

  

    @GetMapping("/blogs/{field}")
    public ResponseEntity<?> getBlogByField(@PathVariable(name="field") String field) { 
        List<Blog> blogs = blogService.findBlogWithSorting(field);
        return ResponseEntity.ok(blogs);
    }
}