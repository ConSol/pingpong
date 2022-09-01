package de.consol.dus.reporter.report;

import de.consol.dus.reporter.boundary.http.endpoint.ReportDto;
import de.consol.dus.reporter.boundary.persistence.ReportEntity;
import de.consol.dus.reporter.boundary.persistence.ReportRepository;
import de.consol.dus.reporter.report.mapper.ReportMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportMapper reportMapper;
  private final ReportRepository reportRepository;

  public ReportDto saveOrUpdateReport(ReportDto toSave) {
    ReportEntity entity = reportRepository.findByGameId(toSave.getGameId())
        .orElseGet(() -> reportMapper.dtoToEntity(toSave));
    entity.setTimePing(toSave.getTimePing());
    entity.setTimePong(toSave.getTimePong());
    if (toSave.getTimePing() < toSave.getTimePong()) {
      entity.setWinner("ping");
    } else if (toSave.getTimePing() < toSave.getTimePong()) {
      entity.setWinner("pong");
    } else {
      entity.setWinner("draw");
    }
    return reportMapper.entityToDto(reportRepository.save(entity));
  }

  public List<ReportDto> getAllReports() {
    return reportRepository.findAll().stream()
        .map(reportMapper::entityToDto)
        .collect(Collectors.toList());
  }

  public Optional<ReportDto> findByGameId(String gameId) {
    return reportRepository.findByGameId(gameId).map(reportMapper::entityToDto);
  }
}
