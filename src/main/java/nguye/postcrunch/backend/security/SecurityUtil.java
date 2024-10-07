package nguye.postcrunch.backend.security;

import nguye.postcrunch.backend.content.ContentEntity;
import nguye.postcrunch.backend.content.ContentRepository;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.AuthCredential;
import nguye.postcrunch.backend.security.auth.AuthService;
import nguye.postcrunch.backend.vote.VoteEntity;
import nguye.postcrunch.backend.vote.VoteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUtil {

  private final AuthService authService;
  private final ContentRepository contentRepository;
  private final VoteRepository voteRepository;

  public SecurityUtil(AuthService authService, ContentRepository contentRepository, VoteRepository voteRepository) {
    this.authService = authService;
    this.contentRepository = contentRepository;
    this.voteRepository = voteRepository;
  }

  public boolean isSenderUser(String userId) {

    AuthCredential principal = (AuthCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (Objects.isNull(principal)) {
      return false;
    }

    String senderId = authService.getUserByUsername(principal.getUsername()).getId();
    return senderId.equals(userId);
  }

  public boolean isSenderContentAuthor(String contentId) {

    ContentEntity entity = contentRepository.findById(contentId).orElseThrow(
        ResourceNotFoundException::new
    );
    return isSenderUser(entity.getAuthor().getId());
  }

  public boolean isSenderVoteAuthor(String voteId) {

    VoteEntity entity = voteRepository.findById(voteId).orElseThrow(
        ResourceNotFoundException::new
    );
    return isSenderUser(entity.getAuthor().getId());
  }
}
