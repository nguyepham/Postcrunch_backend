package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.util.AuthorInfoExtractor;
import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.post.PostController;
import nguye.postcrunch.backend.vote.VoteService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<CommentEntity, Comment> {

  private final CommentService service;
  private final VoteService voteService;

  public CommentRepresentationModelAssembler(CommentService service, VoteService voteService) {
    super(CommentController.class, Comment.class);
    this.service = service;
    this.voteService = voteService;
  }

  public Comment toAuthorizedModel(CommentEntity entity) {
    return toModel(entity)
        .add(linkTo(methodOn(CommentController.class).updateComment(null)).withRel("update"))
        .add(linkTo(methodOn(CommentController.class).deleteCommentById(entity.getId())).withRel("delete"));
  }

  @Override
  public Comment toModel(CommentEntity entity) {
    Comment resource = new Comment();

    switch (entity.getTarget().getContentType()) {
      case "POST" -> resource.add(
          linkTo(methodOn(PostController.class)
              .getPostById(entity.getTarget().getId())).withRel("target"));
      case "COMMENT" -> resource.add(
          linkTo(methodOn(CommentController.class)
              .getCommentById(entity.getTarget().getId())).withRel("target"));
    }

    List<Integer> numVotes = voteService.getNumVotesByTargetId(entity.getId());

    Timestamp createdAt = entity.getCreatedAt();
    Timestamp updatedAt = entity.getUpdatedAt();

    return resource
        .add(linkTo(methodOn(CommentController.class).getCommentById(entity.getId())).withSelfRel())
        .id(entity.getId())
        .contentType(Comment.ContentTypeEnum.COMMENT)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .author(AuthorInfoExtractor.extractAuthorInfo(entity.getAuthor()))
        .targetId(entity.getTarget().getId())
        .edited(!createdAt.equals(updatedAt))
        .numComments(service.getNumCommentsByTargetId(entity.getId()))
        .numUpVotes(numVotes.get(0))
        .numDownVotes(numVotes.get(1))
        .text(entity.getText());
  }

  public List<Comment> toAuthorizedListModel(Iterable<CommentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toAuthorizedModel)
        .collect(toList());
  }

  public List<Comment> toListModel(Iterable<CommentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
