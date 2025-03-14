package com.example.blogapplication.Services;

import java.util.List;

import org.springframework.data.domain.Page;


import com.example.blogapplication.Dto.BlogDto;
import com.example.blogapplication.Entities.Blog;
import com.example.blogapplication.Entities.User;

public interface BlogService {
   
    Page<Blog> findBlogByPagination(int offset,int pageSize);
    Blog createBlog(BlogDto blogDto,User user);
    Blog getBlogById(String id);
    Blog updateBlog(Blog blog);
    void deleteBlogById(String id);
    List<Blog> getAllBlogs();
    List<Blog> findByUserId(String userId);
    List<Blog> findBlogWithSorting(String field);
}
