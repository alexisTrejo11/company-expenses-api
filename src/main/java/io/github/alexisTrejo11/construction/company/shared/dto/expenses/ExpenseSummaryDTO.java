package io.github.alexisTrejo11.construction.company.shared.dto.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseSummaryDTO {
    private Double totalAmount;
    private Long totalCount ;

    public ExpenseSummaryDTO() {
        this.totalCount = 0L;
        this.totalAmount = 0.0;
    }
}
