package nguye.postcrunch.backend.vote;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepositoryExt{

  @PersistenceContext
  private final EntityManager entityManager;

  public VoteRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<VoteEntity> getVotesByAuthorId(String authorId, int page, int size) {
    TypedQuery<VoteEntity> query = entityManager.createQuery(
        "select vote from VoteEntity vote " +
            "where vote.author.id = :authorId " +
            "order by vote.createdAt desc",
        VoteEntity.class
    );

    query.setParameter("authorId", authorId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<VoteEntity> getVotesByTargetId(String targetId, int page, int size) {
    TypedQuery<VoteEntity> query = entityManager.createQuery(
        "select vote from VoteEntity vote " +
            "where vote.target.id = :targetId " +
            "order by vote.createdAt desc",
        VoteEntity.class
    );

    query.setParameter("targetId", targetId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }
}
