package nguye.postcrunch.backend.content;

import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.post.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository
    extends PagingAndSortingRepository<ContentEntity, String>, CrudRepository<ContentEntity, String> {

  @Query("select post from PostEntity post " +
      "where post.author.id = :authorId")
  Optional<List<PostEntity>> getPostsByAuthorId(@Param("authorId") String authorId);

  @Query("select comment from CommentEntity comment " +
      "where comment.author.id = :authorId")
  Optional<List<CommentEntity>> getCommentsByAuthorId(@Param("authorId") String authorId);

  @Query("select comment from CommentEntity comment " +
      "where comment.target.id = :contentId")
  Optional<List<CommentEntity>> getCommentsByContentId(@Param("contentId") String contentId);
}
