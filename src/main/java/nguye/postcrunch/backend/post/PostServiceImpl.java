package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.content.ContentEntity;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.NewPost;
import nguye.postcrunch.backend.content.ContentRepository;
import nguye.postcrunch.backend.model.Post;
import nguye.postcrunch.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

  private final ContentRepository repository;
  private final UserService userService;

  public PostServiceImpl(ContentRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  @Override
  public PostEntity createPost(NewPost newPost) {

    PostEntity entity = new PostEntity();

    entity.setAuthor(userService.getUserById(newPost.getAuthorId()).orElseThrow(
        ResourceNotFoundException::new
    ));
    if (Objects.isNull(newPost.getTitle())) {
      entity.setTitle("No Title");
    } else {
      entity.setTitle(newPost.getTitle());
    }
    entity.setText(newPost.getText());

    return repository.save(entity);
  }

  @Override
  public PostEntity getPostById(String id) {
    Optional<ContentEntity> optPost = repository.findById(id);
    if (optPost.isEmpty()) {
      throw new ResourceNotFoundException();
    }
    return (PostEntity) optPost.get();
  }

  @Override
  public List<PostEntity> getPostsOrderByUpdatedAt(int page, int size) {
    return repository.getPostsOrderByUpdatedAt(page, size);
  }

  @Override
  public List<PostEntity> getPostsOrderByVotes(String requestedAt, int page, int size) throws ParseException {
    return repository.getPostsOrderByVotes(requestedAt, page, size);
  }

  @Override
  public List<PostEntity> getPostsByAuthorIdOrderByUpdatedAt(String authorId, int page, int size) {
    return repository.getPostsByAuthorIdOrderByUpdatedAt(authorId, page, size);
  }

  @Override
  public List<PostEntity> getPostsByAuthorIdOrderByVotes(String authorId, int page, int size) {
    return repository.getPostsByAuthorIdOrderByVotes(authorId, page, size);
  }

  @Override
  public PostEntity updatePost(Post updatedPost) {

    PostEntity entity = (PostEntity) repository.findById(updatedPost.getId()).orElseThrow(
        ResourceNotFoundException::new
    );

    entity.setUpdatedAt(new Timestamp(updatedPost.getUpdatedAt().getTime()));

    if (Objects.nonNull(updatedPost.getTitle())) {
      entity.setTitle(updatedPost.getTitle());
    }

    if (Objects.nonNull(updatedPost.getText())) {
      entity.setText(updatedPost.getText());
    }
    return repository.save(entity);
  }

  @Override
  public void deletePostById(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    }
  }
}
