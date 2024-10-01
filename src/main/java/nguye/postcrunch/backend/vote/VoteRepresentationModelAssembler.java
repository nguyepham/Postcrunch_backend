package nguye.postcrunch.backend.vote;

import nguye.postcrunch.backend.AppUtil;
import nguye.postcrunch.backend.comment.CommentController;
import nguye.postcrunch.backend.model.Vote;
import nguye.postcrunch.backend.post.PostController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VoteRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<VoteEntity, Vote> {

  public VoteRepresentationModelAssembler() {
    super(VoteController.class, Vote.class);
  }

  @Override
  public Vote toModel(VoteEntity entity) {
    Vote resource = new Vote();

    switch (entity.getTarget().getContentType()) {
      case "POST" -> resource.add(
          linkTo(methodOn(PostController.class)
              .getPostById(entity.getTarget().getId())).withRel("target"));
      case "COMMENT" -> resource.add(
          linkTo(methodOn(CommentController.class)
              .getCommentById(entity.getTarget().getId())).withRel("target"));
    }

    return resource
        .id(entity.getId())
        .voteType(Vote.VoteTypeEnum.valueOf(entity.getVoteType()))
        .createdAt(entity.getCreatedAt())
        .author(AppUtil.extractAuthorInfo(entity.getAuthor()))
        .authorId(entity.getAuthor().getId())
        .targetId(entity.getTarget().getId());
  }

  public List<Vote> toListModel(Iterable<VoteEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
