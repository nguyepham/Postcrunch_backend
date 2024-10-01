package nguye.postcrunch.backend.vote;

import org.springframework.data.repository.CrudRepository;

public interface VoteRepository
    extends CrudRepository<VoteEntity, String>, VoteRepositoryExt {
}
