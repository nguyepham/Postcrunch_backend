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

  public AuthorInfo extractAuthorInfo(UserEntity entity) {
    AuthorInfo info = new AuthorInfo();

    info.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());

    return info
        .id(entity.getId())
        .username(entity.getUsername())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName());
  }

  public List<ContentPreview> toPostPreviews(List<Post> posts) {
    List<ContentPreview> previews = new ArrayList<>();

    posts.forEach(post -> {
      ContentPreview preview = new ContentPreview()
          .add(linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel())
          .id(post.getId())
          .contentType(post.getContentType().toString())
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

  public List<ContentPreview> toCommentPreviews(List<Comment> comments) {
    List<ContentPreview> previews = new ArrayList<>();

    comments.forEach(comment -> {
      ContentPreview preview = new ContentPreview()
          .add(linkTo(methodOn(CommentController.class).getCommentById(comment.getId())).withSelfRel())
          .id(comment.getId())
          .contentType(comment.getContentType().toString())
          .createdAt(comment.getCreatedAt())
          .updatedAt(comment.getUpdatedAt())
          .author(comment.getAuthor())
          .targetId(comment.getTarget())
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
