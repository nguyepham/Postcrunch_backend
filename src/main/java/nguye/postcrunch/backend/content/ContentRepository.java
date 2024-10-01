package nguye.postcrunch.backend.content;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentRepository
    extends CrudRepository<ContentEntity, String>, ContentRepositoryExt {

  @Query("select count(comment) from CommentEntity comment " +
        "where comment.target.id = :targetId")
  int getNumCommentsByTargetId(@Param("targetId") String targetId);
}
