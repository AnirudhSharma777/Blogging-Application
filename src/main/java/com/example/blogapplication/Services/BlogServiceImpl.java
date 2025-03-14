package com.example.blogapplication.Services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogapplication.Dto.BlogDto;
import com.example.blogapplication.Entities.Blog;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.BlogNotFoundException;
import com.example.blogapplication.Repositories.BlogRepository;
import com.example.blogapplication.Utils.ServiceUtil;


@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;
    private ServiceUtil serviceUtil;

    public BlogServiceImpl(BlogRepository blogRepository, ServiceUtil serviceUtil) {
        this.blogRepository = blogRepository;
        this.serviceUtil = serviceUtil;
    }

   
    @Override
    public Blog createBlog(BlogDto blogDto, User user) {
        Blog newBlog = Blog.builder()
                .title(blogDto.getTitle())
                .content(blogDto.getContent())
                .author(blogDto.getAuthor())
                .createdAt(serviceUtil.convertDateToString())
                .userId(user.getId())
                .build();
        blogRepository.save(newBlog);
        return newBlog;
    }

    @Override
    public Blog getBlogById(String id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + id));
    }

    @Override
    public Blog updateBlog(Blog blog) {
        Blog existingBlog = blogRepository.findById(blog.getId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + blog.getId()));

        if (blog.getTitle() != null)
            existingBlog.setTitle(blog.getTitle());
        if (blog.getContent() != null)
            existingBlog.setContent(blog.getContent());
        if (blog.getAuthor() != null)
            existingBlog.setAuthor(blog.getAuthor());

        return blogRepository.save(existingBlog);
    }

    @Override
    public void deleteBlogById(String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found with ID: " + id));

        blogRepository.delete(blog);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> findByUserId(String userId) {
        return blogRepository.findByUserId(userId);
    }

    @Override
    public List<Blog> findBlogWithSorting(String field) {
        return blogRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }


    @Override
    public Page<Blog> findBlogByPagination(int offset, int pageSize) {
        return blogRepository.findAll(PageRequest.of(offset,pageSize));
    }





}
