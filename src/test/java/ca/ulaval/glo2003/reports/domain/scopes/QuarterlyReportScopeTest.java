package ca.ulaval.glo2003.reports.domain.scopes;

import ca.ulaval.glo2003.reports.domain.ReportPeriod;
import ca.ulaval.glo2003.time.domain.TimePeriod;
import ca.ulaval.glo2003.time.domain.TimeQuarter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ca.ulaval.glo2003.time.domain.helpers.TimeQuarterBuilder.aTimeQuarter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuarterlyReportScopeTest {

    private static QuarterlyReportScope reportScope;
    private static TimePeriod period = mock(TimePeriod.class);
    private static TimeQuarter firstQuarter = aTimeQuarter().build();
    private static TimeQuarter secondQuarter = firstQuarter.plusQuarters(1);

    @BeforeEach
    public void resetMocks() {
        reset(period);
    }

    private void setUpReportScopeWithSingleQuarter() {
        when(period.getQuarters()).thenReturn(Collections.singletonList(firstQuarter));
        setUpReportScope();
    }

    private void setUpReportScopeWithMultipleQuarters() {
        when(period.getQuarters()).thenReturn(Arrays.asList(firstQuarter, secondQuarter));
        setUpReportScope();
    }

    private void setUpReportScope() {
        reportScope = new QuarterlyReportScope(period);
    }

    @Test
    public void getReportPeriod_withSingleQuarter_shouldHaveSingleReport() {
        setUpReportScopeWithSingleQuarter();

        List<ReportPeriod> reportPeriods = reportScope.getReportPeriods();

        assertEquals(1, reportPeriods.size());
    }

    @Test
    public void getReportPeriod_withSingleQuarter_shouldSetPeriodNamesToThatQuarter() {
        setUpReportScopeWithSingleQuarter();

        List<ReportPeriod> reportPeriods = reportScope.getReportPeriods();

        assertEquals(firstQuarter.toString(), reportPeriods.get(0).getName());
    }

    @Test
    public void getReportPeriod_withSingleQuarter_shouldSetPeriodToThatQuarter() {
        setUpReportScopeWithSingleQuarter();

        List<ReportPeriod> reportPeriods = reportScope.getReportPeriods();

        assertEquals(firstQuarter.toPeriod(), reportPeriods.get(0).getPeriod());
    }

    @Test
    public void getReportPeriod_withMultipleQuartersInSameYear_shouldHaveSingleReport() {
        setUpReportScopeWithMultipleQuarters();

        List<ReportPeriod> reportPeriods = reportScope.getReportPeriods();

        assertEquals(2, reportPeriods.size());
    }

    @Test
    public void getReportPeriod_withMultipleQuartersInSameYear_shouldSetPeriodNamesToThoseQuarters() {
        setUpReportScopeWithMultipleQuarters();

        List<ReportPeriod> reportPeriods = reportScope.getReportPeriods();

        assertEquals(firstQuarter.toString(), reportPeriods.get(0).getName());
        assertEquals(secondQuarter.toString(), reportPeriods.get(1).getName());
    }
}
