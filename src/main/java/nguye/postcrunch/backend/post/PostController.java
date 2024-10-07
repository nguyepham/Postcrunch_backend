package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.api.PostApi;
import nguye.postcrunch.backend.model.GetNewsfeedReq;
import nguye.postcrunch.backend.model.NewPost;
import nguye.postcrunch.backend.model.Post;
import nguye.postcrunch.backend.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class PostController implements PostApi {

  private final PostRepresentationModelAssembler assembler;
  private final PostService service;
  private final SecurityUtil securityUtil;

  public PostController(PostRepresentationModelAssembler assembler, PostService service, SecurityUtil securityUtil) {
    this.assembler = assembler;
    this.service = service;
    this.securityUtil = securityUtil;
  }

  @Override
  public ResponseEntity<Post> createPost(NewPost newPost) {
    
    if (securityUtil.isSenderUser(newPost.getAuthorId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.createPost(newPost)));
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<List<Post>> getNewsfeed(GetNewsfeedReq req, Boolean latest, Integer page, Integer size) throws ParseException {

    List<PostEntity> entities = (latest)?
        service.getPostsOrderByUpdatedAt(page, size):
        service.getPostsOrderByVotes(req.getRequestedTime(), page, size);
    
    List<Post> listModel = new ArrayList<>(); 
    
    entities.forEach(entity -> listModel.add(
        (securityUtil.isSenderUser(entity.getAuthor().getId()))?
        assembler.toAuthorizedModel(entity):
        assembler.toModel(entity)
    ));

    return ResponseEntity.ok(listModel);
  }

  @Override
  public ResponseEntity<Post> getPostById(String id) {

    PostEntity entity = service.getPostById(id);

    if (securityUtil.isSenderUser(entity.getAuthor().getId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(entity));
    }
    return ResponseEntity.ok(assembler.toModel(entity));
  }

  @Override
  public ResponseEntity<List<Post>> getPostsByAuthorId(String id, Boolean latest, Integer page, Integer size) {

    List<PostEntity> entities = (latest)?
        service.getPostsByAuthorIdOrderByUpdatedAt(id, page, size):
        service.getPostsByAuthorIdOrderByVotes(id, page, size);

    if (securityUtil.isSenderUser(id)) {
      return ResponseEntity.ok(assembler.toAuthorizedListModel(entities));
    }
    return ResponseEntity.ok(assembler.toListModel(entities));
  }

  @Override
  public ResponseEntity<Post> updatePost(Post updatedPost) {

    if (Objects.isNull(updatedPost.getId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (securityUtil.isSenderContentAuthor(updatedPost.getId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.updatePost(updatedPost)));
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<Void> deletePostById(String id) {

    if (securityUtil.isSenderContentAuthor(id)) {
      service.deletePostById(id);
      return ResponseEntity.accepted().build();
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }
}
