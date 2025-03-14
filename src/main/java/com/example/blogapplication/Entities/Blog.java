package com.example.blogapplication.Entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    private String createdAt;

    private String updatedAt;
    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    @Column(
        name ="user_id"
    )
    private String userId;
}
