package nguye.postcrunch.backend.vote;

import nguye.postcrunch.backend.api.VoteApi;
import nguye.postcrunch.backend.model.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class VoteController implements VoteApi {

  private final VoteRepresentationModelAssembler assembler;
  private final VoteService service;

  public VoteController(VoteRepresentationModelAssembler assembler, VoteService service) {
    this.assembler = assembler;
    this.service = service;
  }

  @Override
  public ResponseEntity<Vote> createVote(Vote newVote) {
    return ResponseEntity.ok(assembler.toModel(service.createVote(newVote)));
  }

  @Override
  public ResponseEntity<List<Vote>> getVotesByAuthorId(String id, Integer page, Integer size){
    return ResponseEntity.ok(assembler.toListModel(
        service.getVotesByAuthorId(id, page, size)
    ));
  }

  @Override
  public ResponseEntity<List<Vote>> getVotesByTargetId(String id, Integer page, Integer size){
    return ResponseEntity.ok(assembler.toListModel(
        service.getVotesByTargetId(id, page, size)
    ));
  }

  @Override
  public ResponseEntity<Void> deleteVoteById(String id) {
    service.deleteVoteById(id);
    return ResponseEntity.noContent().build();
  }
}
