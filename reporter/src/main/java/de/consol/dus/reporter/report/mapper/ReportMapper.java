package de.consol.dus.reporter.report.mapper;

import de.consol.dus.reporter.boundary.http.endpoint.ReportResponse;
import de.consol.dus.reporter.boundary.http.endpoint.SaveReportRequest;
import de.consol.dus.reporter.boundary.persistence.ReportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
  ReportEntity requestToEntity(SaveReportRequest dto);
  ReportResponse entityToResponse(ReportEntity entity);
}
