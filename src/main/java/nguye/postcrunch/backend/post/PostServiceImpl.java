package nguye.postcrunch.backend.post;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository repository;

  public PostServiceImpl(PostRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<PostEntity> getPostById(String id) {
    return repository.findById(id);
  }

  @Override
  public PostEntity savePost(PostEntity entity) {
    return repository.save(entity);
  }

  @Override
  public void deletePostById(String id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<List<PostEntity>> getPostsByAuthorId(String authorId) {
    return repository.getPostsByAuthorId(authorId);
  }
}
