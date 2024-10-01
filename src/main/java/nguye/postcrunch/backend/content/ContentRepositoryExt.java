package nguye.postcrunch.backend.content;

import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.post.PostEntity;
import org.springframework.data.repository.query.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ContentRepositoryExt {

  List<PostEntity> getPostsOrderByUpdatedAt(
      @Param("page") int page,
      @Param("size") int size
  );

  List<PostEntity> getPostsOrderByVotes(
      @Param("requestedAt") String requestedAt,
      @Param("page") int page,
      @Param("size") int size
  ) throws ParseException;

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
