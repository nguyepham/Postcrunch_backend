package nguye.postcrunch.backend.vote;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VoteRepository
    extends CrudRepository<VoteEntity, String>, VoteRepositoryExt {

  @Query("select count(vote) from VoteEntity vote " +
        "where vote.target.id = :targetId and vote.voteType = \"UP\"")
  int getNumUpVotesByTargetId(@Param("targetId") String targetId);

  @Query("select count(vote) from VoteEntity vote " +
      "where vote.target.id = :targetId and vote.voteType = \"DOWN\"")
  int getNumDownVotesByTargetId(@Param("targetId") String targetId);
}
