package de.consol.dus.reporter.report;

import de.consol.dus.reporter.boundary.http.client.Fruits;
import de.consol.dus.reporter.boundary.http.endpoint.ReportResponse;
import de.consol.dus.reporter.boundary.http.endpoint.SaveReportRequest;
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
  private final Fruits fruits;

  public ReportResponse saveOrUpdateReport(SaveReportRequest toSave) {
    ReportEntity entity = reportRepository.findByGameId(toSave.getGameId())
        .orElseGet(() -> reportMapper.requestToEntity(toSave));
    entity.setTimePing(toSave.getTimePing());
    entity.setTimePong(toSave.getTimePong());
    if (entity.getTimePing() < entity.getTimePong()) {
      entity.setWinner("ping");
      entity.setReward(fruits.getRandomFruitName());
    } else if (entity.getTimePing() > entity.getTimePong()) {
      entity.setWinner("pong");
      entity.setReward(fruits.getRandomFruitName());
    } else {
      entity.setWinner("draw");
      entity.setReward(null);
    }
    return reportMapper.entityToResponse(reportRepository.save(entity));
  }

  public List<ReportResponse> getAllReports() {
    return reportRepository.findAll().stream()
        .map(reportMapper::entityToResponse)
        .collect(Collectors.toList());
  }

  public Optional<ReportResponse> findByGameId(String gameId) {
    return reportRepository.findByGameId(gameId).map(reportMapper::entityToResponse);
  }
}
