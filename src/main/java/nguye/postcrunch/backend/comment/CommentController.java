package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.api.CommentApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.model.CommentPreview;
import nguye.postcrunch.backend.model.NewComment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CommentController implements CommentApi {

  private final CommentRepresentationModelAssembler assembler;
  private final CommentService service;

  public CommentController(CommentRepresentationModelAssembler assembler, CommentService service) {
    this.assembler = assembler;
    this.service = service;
  }

  @Override
  public ResponseEntity<Comment> createComment(NewComment newComment) {
    return ResponseEntity.ok(assembler.toModel(service.createComment(newComment)));
  }

  @Override
  public ResponseEntity<Comment> getCommentById(String id) {
    return Optional.of(service.getCommentById(id)).map(assembler::toModel)
        .map(ResponseEntity::ok).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public ResponseEntity<Comment> updateComment(CommentPreview updatedComment) {
    return ResponseEntity.ok(assembler.toModel(service.updateComment(updatedComment)));
  }

  @Override
  public ResponseEntity<Void> deleteCommentById(String id) {
    service.deleteCommentById(id);
    return ResponseEntity.noContent().build();
  }
}
