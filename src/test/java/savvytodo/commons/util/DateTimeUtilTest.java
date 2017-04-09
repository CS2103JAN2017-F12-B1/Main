package savvytodo.commons.util;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import savvytodo.commons.core.Messages;
import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.task.DateTime;

//@@author A0140016B
/**
 * Date time utility test
 */
public class DateTimeUtilTest {

    // @@author A0139924W
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseStringToDateTime_invalidDate() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(DateTimeUtil.MESSAGE_INCORRECT_SYNTAX);

        DateTimeUtil.parseStringToDateTime("abc");
        DateTimeUtil.parseStringToDateTime("hello world");

        DateTimeUtil.parseStringToDateTime("+1");
        DateTimeUtil.parseStringToDateTime("-1");
    }

    @Test
    public void parseStringToDateTime_emptyArgs() throws IllegalValueException {
        String[] expected = new String[] { StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING };
        String[] actual = DateTimeUtil.parseStringToDateTime(StringUtil.STRING_WHITESPACE);

        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);

        actual = DateTimeUtil.parseStringToDateTime(StringUtil.EMPTY_STRING);
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @Test
    public void parseStringToDateTime_singleDateSuccessful() throws IllegalValueException {
        String[] expectedDateTime = { StringUtil.EMPTY_STRING, "01/01/2016 1500" };
        String[] actualDateTime = DateTimeUtil.parseStringToDateTime("1/1/2016 1500");

        assertArrayEquals(expectedDateTime, actualDateTime);
    }

    @Test
    public void parseStringToDateTime_dateRangeSuccessful() throws IllegalValueException {
        String[] expectedDateTime = { "01/01/2016 1500", "02/01/2016 1600" };
        String[] actualDateTime = DateTimeUtil.parseStringToDateTime("1/1/2016 1500 to 2/1/2016 1600");

        assertArrayEquals(expectedDateTime, actualDateTime);
    }

    // @@author A0140022H
    @Test
    public void modifyDate() {
        String dateToModify = "06/09/2016 2200";

        String frequencyDay = "day";
        String frequencyWeek = "week";
        String frequencyMonth = "month";
        String frequencyYear = "year";

        String expectedDay = "07/09/2016 2200";
        String expectedWeek = "13/09/2016 2200";
        String expectedMonth = "06/10/2016 2200";
        String expectedYear = "06/09/2017 2200";

        String modifiedDay = DateTimeUtil.getRecurDate(dateToModify, frequencyDay);
        String modifiedWeek = DateTimeUtil.getRecurDate(dateToModify, frequencyWeek);
        String modifiedMonth = DateTimeUtil.getRecurDate(dateToModify, frequencyMonth);
        String modifiedYear = DateTimeUtil.getRecurDate(dateToModify, frequencyYear);

        assertEquals(expectedDay, modifiedDay);
        assertEquals(expectedWeek, modifiedWeek);
        assertEquals(expectedMonth, modifiedMonth);
        assertEquals(expectedYear, modifiedYear);
    }

    // @@author A0121533W
    @Test
    public void isWithinWeek_dateTimeNullValue_returnFalse() {
        LocalDateTime nullDateTime = null;
        assertFalse(DateTimeUtil.isWithinWeek(nullDateTime));
    }

    @Test
    public void isWithinWeek_dateTimeNotWithinWeek_returnFalse() {
        LocalDateTime nextMonth = LocalDateTime.now().plus(1, ChronoUnit.MONTHS);
        LocalDateTime lastMonth = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        assertFalse(DateTimeUtil.isWithinWeek(nextMonth));
        assertFalse(DateTimeUtil.isWithinWeek(lastMonth));
    }

    @Test
    public void isOverDue_dateTimeNullValue_returnFalse() {
        LocalDateTime nullDateTime = null;
        assertFalse(DateTimeUtil.isOverDue(nullDateTime));
    }

    @Test
    public void isOverDue_dateTimeOverDue_returnTrue() {
        LocalDateTime yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        assertTrue(DateTimeUtil.isOverDue(yesterday));
    }

    @Test
    public void isOverDue_dateTimeNotOverDue_returnFalse() {
        LocalDateTime tomorrow = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        assertFalse(DateTimeUtil.isOverDue(tomorrow));
    }

    // @@author A0124333U
    @Test
    public void isDateTimeWithinRange_emptyDateTimeSource() throws Exception {
        DateTime dateTimeSource = new DateTime(StringUtil.EMPTY_STRING, StringUtil.EMPTY_STRING);
        DateTime dateTimeQuery = new DateTime("17/01/2016 1200", "18/01/2016 1200");
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
    }

    @Test
    public void isDateTimeWithinRange_dateTimeOutOfRange() throws Exception {
        DateTime dateTimeSource = new DateTime("15/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeSource2 = new DateTime("19/01/2016 1200", "20/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("17/01/2016 1200", "18/01/2016 1200");

        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource2, dateTimeQuery));
    }

    @Test
    public void isDateTimeWithinRange_dateTimeWithinRange() throws Exception {
        DateTime dateTimeSource = new DateTime("14/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeQueryFullyInRange = new DateTime("14/01/2016 2000", "15/01/2016 1200");
        DateTime dateTimeQueryPartiallyInRange = new DateTime("13/01/2016 1000", "15/01/2016 1200");

        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryFullyInRange));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryPartiallyInRange));
    }

    @Test
    public void isDateTimeWithinRange_dateTimeWithoutStartDate() throws Exception {
        DateTime dateTimeSource = new DateTime("15/01/2016 1200", "17/01/2016 1100");
        DateTime dateTimeSourceWithoutStartDate = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("14/01/2016 2000", "17/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate2 = new DateTime("", "18/01/2016 1200");

        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryWithoutStartDate2));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQuery));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQueryWithoutStartDate));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQueryWithoutStartDate2));
    }

    @Test
    public void isDateTimeConflicting_dateTimeConflicts() throws Exception {
        DateTime dateTimeSource = new DateTime("14/01/2016 1200", "16/01/2016 1200");
        DateTime conflictingDateTimeQuery = new DateTime("14/01/2016 2000", "15/01/2016 1200");
        DateTime conflictingDateTimeQuery2 = new DateTime("13/01/2016 1000", "15/01/2016 1200");

        assertTrue(DateTimeUtil.isDateTimeConflict(dateTimeSource, conflictingDateTimeQuery));
        assertTrue(DateTimeUtil.isDateTimeConflict(dateTimeSource, conflictingDateTimeQuery2));
    }

    @Test
    public void isDateTimeConflicting_dateTimeNotConflicting() throws Exception {
        DateTime dateTimeSource = new DateTime("14/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeQueryOutOfRange = new DateTime("18/01/2016 2000", "19/01/2016 1200");
        DateTime dateTimeAdjacent = new DateTime("13/01/2016 1000", "14/01/2016 1200");

        assertFalse(DateTimeUtil.isDateTimeConflict(dateTimeSource, dateTimeQueryOutOfRange));
        assertFalse(DateTimeUtil.isDateTimeConflict(dateTimeSource, dateTimeAdjacent));
    }

    @Test
    public void getListOfFreeTimeSlotsInDate_success() throws DateTimeException, IllegalValueException {
        ArrayList<DateTime> listOfFilledTimeSlots = new ArrayList<DateTime>();
        DateTime dateToCheck = new DateTime("29/10/2016 0000", "29/10/2016 2359");
        ArrayList<DateTime> currentList = new ArrayList<DateTime>();
        ArrayList<DateTime> expectedList = new ArrayList<DateTime>();

        // Initialize listOfFilledTimeSlots
        listOfFilledTimeSlots.add(new DateTime("27/10/2016 1200", "29/10/2016 0830"));
        listOfFilledTimeSlots.add(new DateTime("29/10/2016 0500", "29/10/2016 0630"));
        listOfFilledTimeSlots.add(new DateTime("29/10/2016 0730", "29/10/2016 0900"));
        listOfFilledTimeSlots.add(new DateTime("", "29/10/2016 1300"));
        listOfFilledTimeSlots.add(new DateTime("29/10/2016 1400", "29/10/2016 1500"));
        listOfFilledTimeSlots.add(new DateTime("29/10/2016 2330", "30/10/2016 0100"));

        // Initialize expectedList
        expectedList.add(new DateTime("29/10/2016 0900", "29/10/2016 1400"));
        expectedList.add(new DateTime("29/10/2016 1500", "29/10/2016 2330"));

        currentList = DateTimeUtil.getListOfFreeTimeSlotsInDate(dateToCheck, listOfFilledTimeSlots);

        assertEquals(expectedList, currentList);
    }

    @Test
    public void getDurationInMinutesBetweenTwoLocalDateTime_success() {
        LocalDateTime ldt1 = LocalDateTime.of(2016, 10, 29, 9, 36);
        LocalDateTime ldt2 = LocalDateTime.of(2016, 10, 29, 14, 28);

        assertEquals(DateTimeUtil.getDurationBetweenTwoLocalDateTime(ldt1, ldt2), "4 hr 52 min");
    }

}
