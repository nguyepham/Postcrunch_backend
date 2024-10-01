package nguye.postcrunch.backend.vote;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepositoryExt {

  List<VoteEntity> getVotesByAuthorId(
      @Param("authorId") String authorId,
      @Param("page") int page,
      @Param("size") int size
  );

  List<VoteEntity> getVotesByTargetId(
      @Param("targetId") String targetId,
      @Param("page") int page,
      @Param("size") int size
  );
}
