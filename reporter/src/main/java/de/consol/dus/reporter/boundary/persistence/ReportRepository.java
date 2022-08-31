package de.consol.dus.reporter.boundary.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
  public Optional<ReportEntity> findByGameId(String gameId);
}
