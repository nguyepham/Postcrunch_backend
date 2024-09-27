package nguye.postcrunch.backend.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository
    extends PagingAndSortingRepository<PostEntity, String>, CrudRepository<PostEntity, String> {

  @Query("select post from PostEntity post " +
      "where post.author.id = :authorId")
  Optional<List<PostEntity>> getPostsByAuthorId(@Param("authorId") String authorId);
}
