package de.consol.dus.reporter.boundary.http.endpoint;

import de.consol.dus.reporter.report.ReportService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class Reports {
  private final ReportService reportService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReportResponse> saveReport(@RequestBody SaveReportRequest toSave) {
    final ReportResponse response = reportService.saveOrUpdateReport(toSave);
    return ResponseEntity.created(URI.create("reports/" + response.getGameId())).body(response);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReportResponse>> getReports() {
    final List<ReportResponse> reports = reportService.getAllReports();
    return ResponseEntity.ok(reports);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping("{gameId}")
  public ResponseEntity<ReportResponse> getReport(@PathVariable("gameId") String gameId) {
    Optional<ReportResponse> maybeReport = reportService.findByGameId(gameId);
    return maybeReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
