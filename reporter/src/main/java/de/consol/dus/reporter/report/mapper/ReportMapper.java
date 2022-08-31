package de.consol.dus.reporter.report.mapper;

import de.consol.dus.reporter.boundary.http.endpoint.ReportDto;
import de.consol.dus.reporter.boundary.persistence.ReportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
  ReportEntity dtoToEntity(ReportDto dto);
  ReportDto entityToDto(ReportEntity entity);
}
