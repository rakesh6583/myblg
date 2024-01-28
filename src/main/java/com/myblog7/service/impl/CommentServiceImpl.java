package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= maptoEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found with Id:" + postId));
        comment.setPost(post);//for a particular post am saving the comment so we wrote this
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto=mapToDTO(savedComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found with Id:" + postId));
        List<Comment>comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found with Id:" + postId));//first check if post exist
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFound("Comment not found with Id:" + commentId));//then check if comment exist
        CommentDto commentDto = mapToDTO(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
       return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found with Id:" + postId));//first check if post exist
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFound("Comment not found with Id:" + commentId));
         commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDTO(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment maptoEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
