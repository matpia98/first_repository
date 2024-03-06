package pl.programujodpodstaw.springwebjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("posts")
    public ResponseEntity<Iterable<PostDTO>> getAllPosts() {
        Iterable<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Integer id) {
        PostDTO postDTO = postService.getPost(id);
        if (postDTO != null) {
            return ResponseEntity.ok(postDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("posts")
    public ResponseEntity<PostDTO> addPost(@RequestBody Post post) {
        PostDTO postDTO = postService.addPost(post);
        return ResponseEntity.ok(postDTO);
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        PostDTO updatedPost = postService.updatePost(id, post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

}
