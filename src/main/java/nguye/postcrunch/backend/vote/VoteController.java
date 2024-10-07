package nguye.postcrunch.backend.vote;

import nguye.postcrunch.backend.api.VoteApi;
import nguye.postcrunch.backend.model.Vote;
import nguye.postcrunch.backend.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class VoteController implements VoteApi {

  private final VoteRepresentationModelAssembler assembler;
  private final VoteService service;
  private final SecurityUtil securityUtil;

  public VoteController(VoteRepresentationModelAssembler assembler, VoteService service, SecurityUtil securityUtil) {
    this.assembler = assembler;
    this.service = service;
    this.securityUtil = securityUtil;
  }

  @Override
  public ResponseEntity<Vote> createVote(Vote newVote) {

    if (securityUtil.isSenderUser(newVote.getAuthorId())) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.createVote(newVote)));
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<List<Vote>> getVotesByAuthorId(String id, Integer page, Integer size){

    List<VoteEntity> entities = service.getVotesByAuthorId(id, page, size);

    if (securityUtil.isSenderUser(id)) {
      return ResponseEntity.ok(assembler.toAuthorizedListModel(entities));
    }
    return ResponseEntity.ok(assembler.toListModel(entities));
  }

  @Override
  public ResponseEntity<List<Vote>> getVotesByTargetId(String id, Integer page, Integer size){

    List<VoteEntity> entities = service.getVotesByAuthorId(id, page, size);
    List<Vote> listModel = new ArrayList<>();

    entities.forEach(entity -> listModel.add(
        (securityUtil.isSenderUser(entity.getAuthor().getId()))?
            assembler.toAuthorizedModel(entity):
            assembler.toModel(entity)
    ));

    return ResponseEntity.ok(listModel);
  }

  @Override
  public ResponseEntity<Void> deleteVoteById(String id) {

    if (securityUtil.isSenderVoteAuthor(id)) {
      service.deleteVoteById(id);
      return ResponseEntity.accepted().build();
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Override
  public ResponseEntity<List<Integer>> getNumVotesByTargetId(String id) {
    return ResponseEntity.ok(service.getNumVotesByTargetId(id));
  }
}
