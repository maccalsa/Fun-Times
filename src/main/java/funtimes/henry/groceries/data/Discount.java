package funtimes.henry.groceries.data;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Discount {

    private String description;

    // The fields that trigger a discount
    private Product triggerProduct;
    private int triggerQuantity;

    // A product item that is affected.
    private Product targetProduct;
    private BigDecimal discountPercentage;

    // Assume the finest granularity is days.
    private LocalDate validFrom;
    private LocalDate validTo;

    public boolean inDate(LocalDate now) {
        return (validFrom.isEqual(now) || validFrom.isBefore(now)) &&
                (validTo.isEqual(now) || validTo.isAfter(now));

    }
}
