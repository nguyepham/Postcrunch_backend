package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.AppUtil;
import nguye.postcrunch.backend.comment.CommentService;
import nguye.postcrunch.backend.model.Post;
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
public class PostRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<PostEntity, Post> {

  private final VoteService voteService;
  private final CommentService commentService;

  public PostRepresentationModelAssembler(VoteService voteService, CommentService commentService) {
    super(PostController.class, Post.class);
    this.voteService = voteService;
    this.commentService = commentService;
  }

  @Override
  public Post toModel(PostEntity entity) {
    Post resource = new Post();
    resource.add(linkTo(methodOn(PostController.class).getPostById(entity.getId())).withSelfRel());

    Timestamp createdAt = entity.getCreatedAt();
    Timestamp updatedAt = entity.getUpdatedAt();

    if (Objects.nonNull(entity.getAuthor())) {
      resource.setAuthor(AppUtil.extractAuthorInfo(entity.getAuthor()));
    }

    List<Integer> numVotes = voteService.getNumVotesByTargetId(entity.getId());

    return resource
        .id(entity.getId())
        .contentType(Post.ContentTypeEnum.POST)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .edited(!createdAt.equals(updatedAt))
        .title(entity.getTitle())
//        .numComments(comments.size())
        .numUpVotes(numVotes.get(0))
        .numDownVotes(numVotes.get(1))
        .text(entity.getText());
  }

  public List<Post> toListModel(Iterable<PostEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
