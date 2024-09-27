package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.api.PostApi;
import nguye.postcrunch.backend.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
public class PostController implements PostApi {

  private final PostRepresentationModelAssembler assembler;
  private final PostService service;

  public PostController(PostRepresentationModelAssembler assembler, PostService service) {
    this.assembler = assembler;
    this.service = service;
  }

  @Override
  public ResponseEntity<Post> getPostById(String id) {
    return service.getPostById(id).map(assembler::toModel)
        .map(ResponseEntity::ok).orElse(notFound().build());
  }
}
