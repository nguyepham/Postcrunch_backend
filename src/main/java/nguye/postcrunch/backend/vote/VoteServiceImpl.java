package nguye.postcrunch.backend.vote;

import nguye.postcrunch.backend.content.ContentEntity;
import nguye.postcrunch.backend.content.ContentRepository;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.Vote;
import nguye.postcrunch.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

  private final VoteRepository repository;
  private final ContentRepository contentRepository;
  private final UserService userService;

  public VoteServiceImpl(VoteRepository repository, ContentRepository contentRepository, UserService userService) {
    this.repository = repository;
    this.contentRepository = contentRepository;
    this.userService = userService;
  }

  @Override
  public VoteEntity createVote(Vote newVote) {

    ContentEntity target = contentRepository.findById(newVote.getTargetId()).orElseThrow(
        ResourceNotFoundException::new
    );

    VoteEntity entity = new VoteEntity(newVote.getVoteType().toString(), target);

    entity.setAuthor(userService.getUserById(newVote.getAuthorId()).orElseThrow(
        ResourceNotFoundException::new
    ));

    return repository.save(entity);
  }

  @Override
  public List<VoteEntity> getVotesByAuthorId(String authorId, int page, int size) {
    return repository.getVotesByAuthorId(authorId, page, size);
  }

  @Override
  public List<VoteEntity> getVotesByTargetId(String targetId, int page, int size) {
    return repository.getVotesByTargetId(targetId, page, size);
  }

  @Override
  public void deleteVoteById(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    }
  }

  @Override
  public List<Integer> getNumVotesByTargetId(String targetId) {
    return List.of(
        repository.getNumUpVotesByTargetId(targetId),
        repository.getNumDownVotesByTargetId(targetId)
    );
  }
}
