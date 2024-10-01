package nguye.postcrunch.backend.vote;

import nguye.postcrunch.backend.model.Vote;

import java.util.List;

public interface VoteService {

  VoteEntity createVote(Vote newVote);

  List<VoteEntity> getVotesByAuthorId(String authorId, int page, int size);

  List<VoteEntity> getVotesByTargetId(String targetId, int page, int size);

  void deleteVoteById(String id);

  List<Integer> getNumVotesByTargetId(String targetId);
}
