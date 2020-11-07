package funtimes.henry.groceries;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateHolder {

    public final String DATE_FORMAT = "yyyy-MM-dd";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    private LocalDate current = LocalDate.now();

    public LocalDate getCurrentDate() {
        return current;
    }

    public void setCurrentDate(LocalDate localDate) {
        current = localDate;
    }

    public String getDateString() {
        return simpleDateFormat.format(Date.valueOf(current));
    }

    public void setCurrentDate(String value) throws ParseException {
        current = simpleDateFormat.parse(value).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
