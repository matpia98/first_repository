package pl.programujodpodstaw.springwebjpa;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    public static PostDTO mapperPostDTO(Post post, boolean excludeDetails) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setText(post.getText());
        UserDTO userDTO = UserService.mapperUserDTO(post.getUser(), excludeDetails);
        postDTO.setUser(userDTO);
        return postDTO;
    }


    public Iterable<PostDTO> getAllPosts() {
        Iterable<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = mapperPostDTO(post, true);
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    public PostDTO getPost(Integer id) {
        return postRepository.findById(id)
                .map(post -> mapperPostDTO(post, true))
                .orElse(null);
    }

    public PostDTO addPost(Post post) {
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        PostDTO postDTO = mapperPostDTO(savedPost, true);
        return postDTO;
    }

    public PostDTO updatePost(Integer id, Post post) {
        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setText(post.getText());
                    Post updatedPost = postRepository.save(existingPost);
                    return mapperPostDTO(updatedPost, true);
                })
                .orElse(null);
    }

    public boolean deletePost(Integer id) {
        if (!postRepository.existsById(id)) {
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

}
