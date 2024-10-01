package nguye.postcrunch.backend.content;

import nguye.postcrunch.backend.comment.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository
    extends CrudRepository<ContentEntity, String>, ContentRepositoryExt {

  @Query("select comment from CommentEntity comment " +
      "where comment.author.id = :authorId")
  Optional<List<CommentEntity>> getCommentsByAuthorId(@Param("authorId") String authorId);

  @Query("select comment from CommentEntity comment " +
      "where comment.target.id = :targetId")
  Optional<List<CommentEntity>> getCommentsByTargetId(@Param("targetId") String targetId);
}
