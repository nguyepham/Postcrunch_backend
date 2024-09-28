package nguye.postcrunch.backend.post;

import java.util.List;
import java.util.Optional;

public interface PostService {

  Optional<PostEntity> getPostById(String id);

  PostEntity savePost(PostEntity entity);

  void deletePostById(String id);

  Optional<List<PostEntity>> getPostsByAuthorId(String authorId);
}
