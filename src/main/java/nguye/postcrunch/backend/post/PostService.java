package nguye.postcrunch.backend.post;

import java.util.Optional;

public interface PostService {

  Optional<PostEntity> getPostById(String id);
}
