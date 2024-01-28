package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);//here no dto because from DB what we get is entity object not DTO
}
