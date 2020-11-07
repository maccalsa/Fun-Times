package funtimes.henry.groceries;

import java.time.LocalDate;

public class DateHolder {

    private LocalDate current = LocalDate.now();

    public LocalDate getCurrentDate() {
        return current;
    }

    public void setCurrentDate(LocalDate localDate) {
        current = localDate;
    }
}
