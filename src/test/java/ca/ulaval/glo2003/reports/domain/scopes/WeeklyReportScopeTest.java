package ca.ulaval.glo2003.reports.domain.scopes;

import static ca.ulaval.glo2003.time.domain.helpers.TimeWeekBuilder.aTimeWeek;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.reports.domain.ReportPeriod;
import ca.ulaval.glo2003.time.domain.TimePeriod;
import ca.ulaval.glo2003.time.domain.TimeWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeeklyReportScopeTest {

  private static WeeklyReportScope reportScope;
  private static TimePeriod period = mock(TimePeriod.class);
  private static TimeWeek firstWeek = aTimeWeek().build();
  private static TimeWeek secondWeek = firstWeek.plusWeeks(1);

  @BeforeEach
  public void resetMocks() {
    reset(period);
  }

  private void setUpReportScopeWithSingleWeek() {
    when(period.getWeeks()).thenReturn(Collections.singletonList(firstWeek));
    setUpReportScope();
  }

  private void setUpReportScopeWithMultipleWeeks() {
    when(period.getWeeks()).thenReturn(Arrays.asList(firstWeek, secondWeek));
    setUpReportScope();
  }

  private void setUpReportScope() {
    reportScope = new WeeklyReportScope(period);
  }

  @Test
  public void getReportPeriod_withSingleWeek_shouldHaveSingleReport() {
    setUpReportScopeWithSingleWeek();

    List<ReportPeriod> reportPeriods = reportScope.getPeriods();

    assertEquals(1, reportPeriods.size());
  }

  @Test
  public void getReportPeriod_withSingleWeek_shouldSetPeriodNamesToThatWeek() {
    setUpReportScopeWithSingleWeek();

    List<ReportPeriod> reportPeriods = reportScope.getPeriods();

    assertEquals(firstWeek.toString(), reportPeriods.get(0).getName());
  }

  @Test
  public void getReportPeriod_withSingleWeek_shouldSetPeriodToThatWeek() {
    setUpReportScopeWithSingleWeek();

    List<ReportPeriod> reportPeriods = reportScope.getPeriods();

    assertEquals(firstWeek.toPeriod(), reportPeriods.get(0).getPeriod());
  }

  @Test
  public void getReportPeriod_withMultipleWeeksInSameYear_shouldHaveSingleReport() {
    setUpReportScopeWithMultipleWeeks();

    List<ReportPeriod> reportPeriods = reportScope.getPeriods();

    assertEquals(2, reportPeriods.size());
  }

  @Test
  public void getReportPeriod_withMultipleWeeksInSameYear_shouldSetPeriodNamesToThoseWeeks() {
    setUpReportScopeWithMultipleWeeks();

    List<ReportPeriod> reportPeriods = reportScope.getPeriods();

    assertEquals(firstWeek.toString(), reportPeriods.get(0).getName());
    assertEquals(secondWeek.toString(), reportPeriods.get(1).getName());
  }
}
