package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.api.CommentApi;
import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.model.NewComment;
import nguye.postcrunch.backend.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CommentController implements CommentApi {

  private final CommentRepresentationModelAssembler assembler;
  private final CommentService service;
  private final SecurityUtil securityUtil;

  public CommentController(CommentRepresentationModelAssembler assembler, CommentService service, SecurityUtil securityUtil) {
    this.assembler = assembler;
    this.service = service;
    this.securityUtil = securityUtil;
  }

  @Override
  public ResponseEntity<Comment> createComment(NewComment newComment) {

    if (securityUtil.isSenderUser(newComment.getAuthorId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.createComment(newComment)));
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<Comment> getCommentById(String id) {

    CommentEntity entity = service.getCommentById(id);

    if (securityUtil.isSenderUser(entity.getAuthor().getId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(entity));
    }
    return ResponseEntity.ok(assembler.toModel(entity));
  }

  @Override
  public ResponseEntity<List<Comment>> getCommentsByAuthorId(String id, Boolean latest, Integer page, Integer size) {

    List<CommentEntity> entities = (latest)?
        service.getCommentsByAuthorIdOrderByUpdatedAt(id, page, size):
        service.getCommentsByAuthorIdOrderByVotes(id, page, size);

    if (securityUtil.isSenderUser(id)) {
      return ResponseEntity.ok(assembler.toAuthorizedListModel(entities));
    }
    return ResponseEntity.ok(assembler.toListModel(entities));
  }

  @Override
  public ResponseEntity<List<Comment>> getCommentsByTargetId(String id, Boolean latest, Integer page, Integer size) {

    List<CommentEntity> entities = (latest)?
        service.getCommentsByTargetIdOrderByUpdatedAt(id, page, size):
        service.getCommentsByTargetIdOrderByVotes(id, page, size);

    List<Comment> listModel = new ArrayList<>();

    entities.forEach(entity -> listModel.add(
        (securityUtil.isSenderUser(entity.getAuthor().getId()))?
            assembler.toAuthorizedModel(entity):
            assembler.toModel(entity)
    ));

    return ResponseEntity.ok(listModel);
  }

  @Override
  public ResponseEntity<Comment> updateComment(Comment updatedComment) {

    if (Objects.isNull(updatedComment.getId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (securityUtil.isSenderContentAuthor(updatedComment.getId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.updateComment(updatedComment)));
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<Void> deleteCommentById(String id) {

    if (securityUtil.isSenderContentAuthor(id)) {
      service.deleteCommentById(id);
      return ResponseEntity.accepted().build();
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<Integer> getNumCommentsByTargetId(String id) {
    return ResponseEntity.ok(service.getNumCommentsByTargetId(id));
  }
}
