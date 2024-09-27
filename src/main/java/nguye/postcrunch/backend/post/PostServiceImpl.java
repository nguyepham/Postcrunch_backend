package nguye.postcrunch.backend.post;

import org.springframework.stereotype.Service;

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
}
