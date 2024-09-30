package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.model.NewPost;
import nguye.postcrunch.backend.model.ContentPreview;

import java.util.List;

public interface PostService {

  PostEntity createPost(NewPost newPost);

  PostEntity getPostById(String id);

  PostEntity updatePost(ContentPreview updatedPost);

  void deletePostById(String id);

  List<PostEntity> getPostsByAuthorId(String authorId);
}
