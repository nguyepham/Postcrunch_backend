package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.content.ContentEntity;
import nguye.postcrunch.backend.content.ContentRepository;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.CommentPreview;
import nguye.postcrunch.backend.model.NewComment;
import nguye.postcrunch.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

  private final ContentRepository repository;
  private final UserService userService;

  public CommentServiceImpl(ContentRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  @Override
  public CommentEntity createComment(NewComment newComment) {

    ContentEntity target = repository.findById(newComment.getTargetId()).orElseThrow(
        ResourceNotFoundException::new
    );

    CommentEntity entity = new CommentEntity(target);

    entity.setAuthor(userService.getUserById(newComment.getAuthorId()).orElseThrow(
        ResourceNotFoundException::new
    ));
    entity.setText(newComment.getText());

    return repository.save(entity);
  }

  @Override
  public CommentEntity getCommentById(String id) {
    Optional<ContentEntity> optComment = repository.findById(id);
    if (optComment.isEmpty()) {
      throw new ResourceNotFoundException();
    }
    return (CommentEntity) optComment.get();
  }

  @Override
  public CommentEntity updateComment(CommentPreview updatedComment) {

    CommentEntity entity = (CommentEntity) repository.findById(updatedComment.getId()).orElseThrow(
        ResourceNotFoundException::new
    );

    entity.setUpdatedAt(new Timestamp(updatedComment.getUpdatedAt().getTime()));

    if (Objects.nonNull(updatedComment.getText())) {
      entity.setText(updatedComment.getText());
    }
    return repository.save(entity);
  }

  @Override
  public void deleteCommentById(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    }
  }

  @Override
  public List<CommentEntity> getCommentsByContentId(String contentId) {
    return repository.getCommentsByContentId(contentId).orElse(List.of());
  }
}
