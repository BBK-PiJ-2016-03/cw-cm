package manager;

import java.util.Calendar;

/**
  * @author Alexander Worton.
  */
public final class DateFns {

  private DateFns() {
  }

  private static final Calendar CALENDAR = Calendar.getInstance();
  private static final int YEAR = CALENDAR.get(Calendar.YEAR);
  private static final int MONTH = CALENDAR.get(Calendar.MONTH);
  private static final int LAST_YEAR = YEAR - 1;
  private static final int NEXT_YEAR = YEAR + 1;
  private static final int FIRST_DAY_OF_MONTH = 1;
  private static final int REMOVE = -1;
  private static final int NO_OFFSET = 0;

  private static Calendar getCurrentDate() {
    return Calendar.getInstance();
  }

  /**
   * get a past date.
   * @return a date in the past
   */
  public static Calendar getPastDate() {
    return getPastDate(NO_OFFSET);
  }

  /**
   * get a past date, offset by the provided value.
   * @param offset amount to increase by
   * @return the past date
   */
  public static Calendar getPastDate(int offset) {
    Calendar cal = getCurrentDate();
    cal.set(LAST_YEAR, MONTH, FIRST_DAY_OF_MONTH);
    cal.add(Calendar.MINUTE, offset);
    return cal;
  }

  /**
   * get a future date.
   * @return a date in the future
   */
  public static Calendar getFutureDate() {
    return getFutureDate(NO_OFFSET);
  }

  /**
   * get a future date, offset by the amount provided.
   * @param offset the supplied offset in minutes from the future date
   * @return the future date
   */
  public static Calendar getFutureDate(int offset) {
    Calendar cal = getCurrentDate();
    cal.set(NEXT_YEAR, MONTH, FIRST_DAY_OF_MONTH);
    cal.add(Calendar.MINUTE, offset);
    return cal;
  }

  /**
   * get a future date that is slightly in the future.
   * @return the calendar date slightly in the future
   */
  public static Calendar getSlightlyFutureDate() {
    Calendar cal = getCurrentDate();
    cal.add(Calendar.SECOND, FIRST_DAY_OF_MONTH);
    return cal;
  }

  /**
   * get a past date that is slightly in the past.
   * @return the calendar date slightly in the past
   */
  public static Calendar getSlightlyPastDate() {
    Calendar cal = getCurrentDate();
    cal.add(Calendar.SECOND, REMOVE);
    return cal;
  }
}
