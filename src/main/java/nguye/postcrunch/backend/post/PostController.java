package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.api.PostApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.NewPost;
import nguye.postcrunch.backend.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController implements PostApi {

  private final PostRepresentationModelAssembler assembler;
  private final PostService service;

  public PostController(PostRepresentationModelAssembler assembler, PostService service) {
    this.assembler = assembler;
    this.service = service;
  }

  @Override
  public ResponseEntity<Post> createPost(NewPost newPost) {
    return ResponseEntity.ok(assembler.toModel(service.createPost(newPost)));
  }

  @Override
  public ResponseEntity<Post> getPostById(String id) {
    return Optional.of(service.getPostById(id)).map(assembler::toModel)
        .map(ResponseEntity::ok).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public ResponseEntity<List<Post>> getPostsByAuthorId(String id, Boolean latest, Integer page, Integer size) {

    List<PostEntity> posts = (latest)?
        service.getPostsByAuthorIdOrderByUpdatedAt(id, page, size):
        service.getPostsByAuthorIdOrderByVotes(id, page, size);

    return ResponseEntity.ok(assembler.toListModel(posts));
  }

  @Override
  public ResponseEntity<Post> updatePost(Post updatedPost) {
    return ResponseEntity.ok(assembler.toModel(service.updatePost(updatedPost)));
  }

  @Override
  public ResponseEntity<Void> deletePostById(String id) {
    service.deletePostById(id);
    return ResponseEntity.noContent().build();
  }
}
