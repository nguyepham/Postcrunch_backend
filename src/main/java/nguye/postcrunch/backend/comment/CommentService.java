package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.model.NewComment;

import java.util.List;

public interface CommentService {

  CommentEntity createComment(NewComment newComment);

  CommentEntity getCommentById(String id);

  List<CommentEntity> getCommentsByAuthorId(String authorId);

  List<CommentEntity> getCommentsByTargetId(String targetId);

  CommentEntity updateComment(Comment updatedComment);

  void deleteCommentById(String id);
}
