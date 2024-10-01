package nguye.postcrunch.backend.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.post.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentRepositoryImpl implements ContentRepositoryExt {

  @PersistenceContext
  private final EntityManager entityManager;

  public ContentRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<PostEntity> getPostsByAuthorIdOrderByUpdatedAt(String authorId, int page, int size) {
    TypedQuery<PostEntity> query = entityManager.createQuery(
        "select post from PostEntity post " +
            "where post.author.id = :authorId " +
            "order by post.updatedAt desc",
        PostEntity.class);

    query.setParameter("authorId", authorId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<PostEntity> getPostsByAuthorIdOrderByVotes(String authorId, int page, int size) {
    TypedQuery<PostEntity> query = entityManager.createQuery(
        "select post from PostEntity post " +
            "where post.author.id = :authorId " +
            "order by (select count(vote) from VoteEntity vote " +
                      "where vote.target.id = post.id " +
                      "and vote.voteType = \"UP\") desc, " +
                      "(select count(vote) from VoteEntity vote " +
                      "where vote.target.id = post.id " +
                      "and vote.voteType = 'DOWN') asc",
        PostEntity.class);

    query.setParameter("authorId", authorId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<CommentEntity> getCommentsByAuthorIdOrderByUpdatedAt(String authorId, int page, int size) {
    TypedQuery<CommentEntity> query = entityManager.createQuery(
        "select comment from CommentEntity comment " +
            "where comment.author.id = :authorId " +
            "order by comment.updatedAt desc",
        CommentEntity.class);

    query.setParameter("authorId", authorId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<CommentEntity> getCommentsByAuthorIdOrderByVotes(String authorId, int page, int size) {
    TypedQuery<CommentEntity> query = entityManager.createQuery(
        "select comment from CommentEntity comment " +
            "where comment.author.id = :authorId " +
            "order by (select count(vote) from VoteEntity vote " +
            "where vote.target.id = comment.id " +
            "and vote.voteType = \"UP\") desc, " +
            "(select count(vote) from VoteEntity vote " +
            "where vote.target.id = comment.id " +
            "and vote.voteType = 'DOWN') asc",
        CommentEntity.class);

    query.setParameter("authorId", authorId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<CommentEntity> getCommentsByTargetIdOrderByUpdatedAt(String targetId, int page, int size) {
    TypedQuery<CommentEntity> query = entityManager.createQuery(
        "select comment from CommentEntity comment " +
            "where comment.target.id = :targetId " +
            "order by comment.updatedAt desc",
        CommentEntity.class);

    query.setParameter("targetId", targetId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<CommentEntity> getCommentsByTargetIdOrderByVotes(String targetId, int page, int size) {
    TypedQuery<CommentEntity> query = entityManager.createQuery(
        "select comment from CommentEntity comment " +
            "where comment.target.id = :targetId " +
            "order by (select count(vote) from VoteEntity vote " +
            "where vote.target.id = comment.id " +
            "and vote.voteType = \"UP\") desc, " +
            "(select count(vote) from VoteEntity vote " +
            "where vote.target.id = comment.id " +
            "and vote.voteType = 'DOWN') asc",
        CommentEntity.class);

    query.setParameter("targetId", targetId);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }
}
