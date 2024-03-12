package com.JWT.service;
import com.JWT.entity.Post;
import com.JWT.exception.ResourceNotFoundException;
import com.JWT.payload.PostDTO;
import com.JWT.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;


    public PostDTO createPost(PostDTO postDto) {
        Post post=mapToEntity(postDto);


       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        Post newPost=postRepo.save(post);
        PostDTO dto=mapToDto(newPost);

       dto.setId(newPost.getId());
       dto.setTitle(newPost.getTitle());
       dto.setDescription(newPost.getDescription());
       dto.setContent(newPost.getContent());
        return dto;
    }

    public List<PostDTO> listAllPosts() {
      List<Post> posts=postRepo.findAll();
        List<PostDTO>postDtos=posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    public PostDTO getPostById(long id) {
        Post newPost = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        PostDTO postDto=mapToDto(newPost);
        return postDto;
    }

    public PostDTO updatePost(PostDTO postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        Post newPost= mapToEntity(postDto);
        newPost.setId(id);
        Post updatedPost =postRepo.save(newPost);
        PostDTO dto= mapToDto(updatedPost);
        return dto;
    }

    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        postRepo.deleteById(id);
    }
    private PostDTO mapToDto(Post post) {

        PostDTO dto =new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
    private Post mapToEntity(PostDTO postDto) {

        Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
