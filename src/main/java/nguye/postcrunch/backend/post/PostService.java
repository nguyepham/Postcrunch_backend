package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.model.NewPost;
import nguye.postcrunch.backend.model.Post;

import java.util.List;

public interface PostService {

  PostEntity createPost(NewPost newPost);

  PostEntity getPostById(String id);

  List<PostEntity> getPostsByAuthorIdOrderByUpdatedAt(String authorId, int page, int size);

  List<PostEntity> getPostsByAuthorIdOrderByVotes(String authorId, int page, int size);

  PostEntity updatePost(Post updatedPost);

  void deletePostById(String id);
}
