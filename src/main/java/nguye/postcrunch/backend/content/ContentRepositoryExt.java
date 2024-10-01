package nguye.postcrunch.backend.content;

import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.post.PostEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepositoryExt {

  List<PostEntity> getPostsByAuthorIdOrderByUpdatedAt(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<PostEntity> getPostsByAuthorIdOrderByVotes(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<CommentEntity> getCommentsByAuthorIdOrderByUpdatedAt(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<CommentEntity> getCommentsByAuthorIdOrderByVotes(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<CommentEntity> getCommentsByTargetIdOrderByUpdatedAt(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<CommentEntity> getCommentsByTargetIdOrderByVotes(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );
}
