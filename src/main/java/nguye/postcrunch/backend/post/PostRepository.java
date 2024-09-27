package nguye.postcrunch.backend.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository
    extends PagingAndSortingRepository<PostEntity, String>, CrudRepository<PostEntity, String> {
}
