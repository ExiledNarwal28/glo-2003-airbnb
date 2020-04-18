package ca.ulaval.glo2003.time.domain.helpers;

import static ca.ulaval.glo2003.time.domain.helpers.TimeDayBuilder.aTimeDay;

import ca.ulaval.glo2003.time.domain.TimeDate;
import ca.ulaval.glo2003.time.domain.TimeMonth;
import ca.ulaval.glo2003.time.domain.TimeYear;

public class TimeDateBuilder {

  private TimeDayBuilder timeDayBuilder = aTimeDay();

  public static TimeDateBuilder aTimeDate() {
    return new TimeDateBuilder();
  }

  public TimeDateBuilder withYear(TimeYear year) {
    timeDayBuilder = timeDayBuilder.withYear(year);
    return this;
  }

  public TimeDateBuilder withMonth(TimeMonth month) {
    timeDayBuilder = timeDayBuilder.withMonth(month);
    return this;
  }

  public TimeDate build() {
    return timeDayBuilder.build().toDate();
  }
}
