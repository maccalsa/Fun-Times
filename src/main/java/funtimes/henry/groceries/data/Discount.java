package funtimes.henry.groceries.data;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public class Discount {

    private String description;

    // The fields that trigger a discount
    private Product triggerProduct;
    private int triggerQuantity;

    // A product item that is affected.
    private Product targetProduct;
    private Float discountPercentage;

    // Assume the finest granularity is days.
    private LocalDate validFrom;
    private LocalDate validTo;
}
