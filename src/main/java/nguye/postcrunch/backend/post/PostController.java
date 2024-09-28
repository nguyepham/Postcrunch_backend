package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.api.PostApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.AddPostReq;
import nguye.postcrunch.backend.model.Post;
import nguye.postcrunch.backend.model.PostPreview;
import nguye.postcrunch.backend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Objects;

@RestController
public class PostController implements PostApi {

  private final PostRepresentationModelAssembler assembler;
  private final PostService service;
  private final UserService userService;

  public PostController(PostRepresentationModelAssembler assembler, PostService service, UserService userService) {
    this.assembler = assembler;
    this.service = service;
    this.userService = userService;
  }

  @Override
  public ResponseEntity<Post> createPost(AddPostReq addPostReq) {

    PostEntity entity = new PostEntity();

    entity.setAuthor(userService.getUserById(addPostReq.getAuthorId()).orElseThrow(
        ResourceNotFoundException::new
    ));

    if (Objects.isNull(addPostReq.getTitle())) {
      entity.setTitle("No Title");
    } else {
      entity.setTitle(addPostReq.getTitle());
    }
    entity.setText(addPostReq.getText());

    return ResponseEntity.ok(assembler.toModel(service.savePost(entity)));
  }

  @Override
  public ResponseEntity<Post> updatePost(PostPreview updatedPost) {

    if (Objects.isNull(updatedPost.getId()) ||
        Objects.isNull(updatedPost.getUpdatedAt())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    PostEntity entity = service.getPostById(updatedPost.getId()).orElseThrow(ResourceNotFoundException::new);

    entity.setUpdatedAt(new Timestamp(updatedPost.getUpdatedAt().getTime()));

    if (Objects.nonNull(updatedPost.getTitle())) {
      entity.setTitle(updatedPost.getTitle());
    }

    if (Objects.nonNull(updatedPost.getText())) {
      entity.setText(updatedPost.getText());
    }

    return ResponseEntity.ok(assembler.toModel(service.savePost(entity)));
  }

  @Override
  public ResponseEntity<Post> getPostById(String id) {
    return service.getPostById(id).map(assembler::toModel)
        .map(ResponseEntity::ok).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public ResponseEntity<Void> deletePostById(String id) {
    if (service.getPostById(id).isPresent()) {
      service.deletePostById(id);
      return ResponseEntity.noContent().build();
    }
    throw new ResourceNotFoundException();
  }
}
