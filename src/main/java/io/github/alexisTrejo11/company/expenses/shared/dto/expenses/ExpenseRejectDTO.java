package io.github.alexisTrejo11.company.expenses.shared.dto.expenses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpenseRejectDTO {
    @JsonProperty("expense_id")
    private Long expenseId;

    @JsonProperty("reject_reason")
    private String rejectReason;
}
