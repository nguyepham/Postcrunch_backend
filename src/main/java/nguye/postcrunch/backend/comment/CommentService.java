package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.model.NewComment;

import java.util.List;

public interface CommentService {

  CommentEntity createComment(NewComment newComment);

  CommentEntity getCommentById(String id);

  List<CommentEntity> getCommentsByAuthorIdOrderByUpdatedAt(String authorId, int page, int size);

  List<CommentEntity> getCommentsByTargetIdOrderByUpdatedAt(String targetId, int page, int size);

  List<CommentEntity> getCommentsByAuthorIdOrderByVotes(String authorId, int page, int size);

  List<CommentEntity> getCommentsByTargetIdOrderByVotes(String targetId, int page, int size);

  CommentEntity updateComment(Comment updatedComment);

  void deleteCommentById(String id);

  int getNumCommentsByTargetId(String targetId);
}
