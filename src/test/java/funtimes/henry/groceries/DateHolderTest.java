package funtimes.henry.groceries;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DateHolderTest {

    @Test
    public void getCurrentDate() {
        // given :: by default should be today date
        // when
        LocalDate currentDate = new DateHolder().getCurrentDate();
        // Then
        assertEquals(currentDate, LocalDate.now());
    }

    @Test
    public void setCurrentDate() {
        // given
        DateHolder dateHolder = new DateHolder();

        // when
        dateHolder.setCurrentDate(LocalDate.MAX);

        // then
        assertEquals(LocalDate.MAX, dateHolder.getCurrentDate());
    }
}
