package com.myblog7.service.impl;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;//Internal Library so object is Automatically Created means spring boot internally has this library
    private ModelMapper modelMapper;//External library spring boot does'nt have this library so IOC does'nt create bean for these library

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
         Post post=mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

       PostDto dto=mapToDto(savedPost);
       return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        //first check when the post is available or not if not then throw ResourceNotFound
       Post post= postRepository.findById(id).orElseThrow(
               ()-> new ResourceNotFound("Post not found with id:"+id)
       );//if post is found it will give post obj and store that in post else it will throw exception so we use this instead of if-else

        post.setTitle(postDto.getTitle());//here we are overwriting the above post(Post post= postRepository.findById(id).orElseThrow()) which we got by passing long id with PostDto postDto which also we got by passing  PostDto postDto in public PostDto updatePost(long id, PostDto postDto)
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());


        Post updatePost = postRepository.save(post);
        PostDto dto = mapToDto(updatePost);
        return dto;     }


    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + id));
        PostDto dto= mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?// only sorting information
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();// In these two lines
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);// Here we are implementing findAll based on pageable

        List<Post> posts = pagePosts.getContent(); //the return type of postRepository.findAll(pageable); is Page<Post> & we have to convert it to List so .getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse= new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        return postResponse;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        //PostDto dto= new PostDto();
       // dto.setId(post.getId());
        //dto.setTitle(post.getTitle());
        //dto.setDescription(post.getDescription());
        //dto.setContent(post.getContent());
        return dto;
}
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        // Post post= new Post();//these are used
        //post.setTitle(postDto.getTitle());//to save data
        //post.setDescription(postDto.getDescription());//in DB
        //post.setContent(postDto.getContent());//in MySql workbench
        return post;
    }
}