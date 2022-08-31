package de.consol.dus.reporter.boundary.http.endpoint;

import de.consol.dus.reporter.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class Reports {
  private final ReportService reportService;

  @PostMapping(consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReportDto> saveReport(@RequestBody ReportDto toSave) {
    final ReportDto response = reportService.saveOrUpdateReport(toSave);
    return ResponseEntity.created(null).body(response);
  }
}
