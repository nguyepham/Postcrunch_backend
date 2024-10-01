package nguye.postcrunch.backend.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.post.PostEntity;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

@Repository
public class ContentRepositoryImpl implements ContentRepositoryExt {

  @PersistenceContext
  private final EntityManager entityManager;

  public ContentRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<PostEntity> getPostsOrderByUpdatedAt(int page, int size) {
    TypedQuery<PostEntity> query = entityManager.createQuery(
        "select post from PostEntity post " +
            "order by post.updatedAt desc",
        PostEntity.class);
    // Set pagination parameters
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
  }

  @Override
  public List<PostEntity> getPostsOrderByVotes(String requestedAt, int page, int size) throws ParseException {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(requestedAt));

    // Set the time to 0:00:00 for the current day (start of requestedAt day)
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date startOfRequestedDay = cal.getTime();

    // Calculate 0:00:00 of the day before requestedAt
    cal.add(Calendar.DATE, -1);
    Date startOfDayBefore = cal.getTime();

    TypedQuery<PostEntity> query = entityManager.createQuery(
        "select post from PostEntity post " +
            "order by (select count(vote) from VoteEntity vote " +
                      "where vote.target.id = post.id " +
                      "and vote.voteType = 'UP' " +
                      "and vote.createdAt between :startOfDayBefore and :startOfRequestedDay) desc, " +
                      "(select count(vote) from VoteEntity vote " +
                      "where vote.target.id = post.id " +
                      "and vote.voteType = 'DOWN' " +
                      "and vote.createdAt between :startOfDayBefore and :startOfRequestedDay) asc",
        PostEntity.class);

    // Set parameters
    query.setParameter("startOfDayBefore", startOfDayBefore);
    query.setParameter("startOfRequestedDay", startOfRequestedDay);
    query.setFirstResult(page * size); // Calculate the offset
    query.setMaxResults(size); // Limit the result set to 'size'

    return query.getResultList();
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
                      "and vote.voteType = 'UP') desc, " +
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
            "and vote.voteType = 'UP') desc, " +
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
            "and vote.voteType = 'UP') desc, " +
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
