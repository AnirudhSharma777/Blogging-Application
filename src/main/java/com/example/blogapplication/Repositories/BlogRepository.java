package com.example.blogapplication.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogapplication.Entities.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> {
    List<Blog> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Blog b WHERE b.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
