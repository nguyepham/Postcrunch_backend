package nguye.postcrunch.backend.content;

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
}
