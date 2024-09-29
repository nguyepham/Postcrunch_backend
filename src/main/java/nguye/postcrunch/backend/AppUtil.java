package nguye.postcrunch.backend;

import nguye.postcrunch.backend.comment.CommentController;
import nguye.postcrunch.backend.model.*;
import nguye.postcrunch.backend.post.PostController;
import nguye.postcrunch.backend.user.UserController;
import nguye.postcrunch.backend.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUtil {

  public static AuthorInfo extractAuthorInfo(UserEntity entity) {
    AuthorInfo info = new AuthorInfo();

    info.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());

    return info
        .id(entity.getId())
        .username(entity.getUsername())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName());
  }

  public static List<PostPreview> toPostPreview(List<Post> posts) {
    List<PostPreview> previews = new ArrayList<>();

    posts.forEach(post -> {
      PostPreview preview = new PostPreview()
          .add(linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel())
          .id(post.getId())
          .createdAt(post.getCreatedAt())
          .updatedAt(post.getUpdatedAt())
          .author(post.getAuthor())
          .edited(post.getEdited())
          .title(post.getTitle())
          .text(post.getText())
          .numUpVotes(post.getNumUpVotes())
          .numDownVotes(post.getNumDownVotes())
          .numComments(post.getNumComments());
      previews.add(preview);
    });

    return previews;
  }

  public static List<CommentPreview> toCommentPreview(List<Comment> comments) {
    List<CommentPreview> previews = new ArrayList<>();

    comments.forEach(comment -> {
      CommentPreview preview = new CommentPreview()
          .add(linkTo(methodOn(CommentController.class).getCommentById(comment.getId())).withSelfRel())
          .id(comment.getId())
          .createdAt(comment.getCreatedAt())
          .updatedAt(comment.getUpdatedAt())
          .author(comment.getAuthor())
          .edited(comment.getEdited())
          .text(comment.getText())
          .numUpVotes(comment.getNumUpVotes())
          .numDownVotes(comment.getNumDownVotes())
          .numComments(comment.getNumComments());
      previews.add(preview);
    });

    return previews;
  }
}
