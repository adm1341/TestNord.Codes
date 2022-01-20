package exercise.repository;

import exercise.model.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends CrudRepository<URL, Long> {
    Optional<URL> findByShortUrl(String shortUrl);
}
