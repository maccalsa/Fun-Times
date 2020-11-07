package funtimes.henry.groceries.data;


import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {

    @Getter
    private List<Product> products = new ArrayList<>();

    @Getter
    private List<Discount> discounts = new ArrayList<>();


    /**
     *
     */
    public Database() {
        buildProjects();
        buildDiscounts();
    }

    /**
     *
     */
    private void buildProjects() {
        products.add(Product.builder().name("soup").unit("tin").cost(BigDecimal.valueOf(0.65).setScale(2)).build());
        products.add(Product.builder().name("bread").unit("loaf").cost(BigDecimal.valueOf(0.80).setScale(2)).build());
        products.add(Product.builder().name("milk").unit("bottle").cost(BigDecimal.valueOf(1.30).setScale(2)).build());
        products.add(Product.builder().name("apples").unit("single").cost(BigDecimal.valueOf(0.10).setScale(2)).build());
    }

    /**
     *
     */
    private void buildDiscounts() {
        final LocalDateTime now = LocalDateTime.now();

        discounts.add(Discount.builder()
                .description("Buy 2 tins of soup and get a loaf of bread half price")
                .triggerProduct( products.get(0))
                .triggerQuantity(2)
                .discountPercentage(BigDecimal.valueOf(0.5))
                .targetProduct(products.get(1))
                .validFrom(now.minusDays(1).toLocalDate())
                .validTo(now.plusDays(7).toLocalDate()).build());

        // from 3 days hence
        LocalDate startDate = now.plusDays(3).toLocalDate();
        // until the end of the following month
        LocalDate endDate = startDate.plusMonths(1)
                                .withDayOfMonth(startDate.plusMonths(1).lengthOfMonth());

        discounts.add(Discount.builder()
                .description("Apples have a 10% discount")
                .triggerProduct( products.get(3))
                .triggerQuantity(1)
                .discountPercentage(BigDecimal.valueOf(0.9))
                .targetProduct(products.get(3))
                .validFrom(startDate)
                .validTo(endDate).build());
    }

}
