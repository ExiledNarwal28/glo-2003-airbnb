package ca.ulaval.glo2003.reports.domain.scopes;

import ca.ulaval.glo2003.reports.domain.ReportPeriod;
import ca.ulaval.glo2003.time.domain.TimePeriod;
import ca.ulaval.glo2003.time.domain.TimeYear;
import java.util.List;
import java.util.stream.Collectors;

public class AnnualReportScope extends ReportScope {

  public AnnualReportScope(TimePeriod period) {
    super(period);
  }

  @Override
  public List<ReportPeriod> getPeriods() {
    return period.getYears().stream().map(this::createReportPeriod).collect(Collectors.toList());
  }

  private ReportPeriod createReportPeriod(TimeYear year) {
    return new ReportPeriod(year.toString(), year.toPeriod());
  }
}
