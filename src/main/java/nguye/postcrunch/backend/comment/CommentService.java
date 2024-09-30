package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.model.ContentPreview;
import nguye.postcrunch.backend.model.NewComment;

import java.util.List;

public interface CommentService {

  CommentEntity createComment(NewComment newComment);

  CommentEntity getCommentById(String id);

  CommentEntity updateComment(ContentPreview updatedComment);

  void deleteCommentById(String id);

  List<CommentEntity> getCommentsByContentId(String contentId);
}
