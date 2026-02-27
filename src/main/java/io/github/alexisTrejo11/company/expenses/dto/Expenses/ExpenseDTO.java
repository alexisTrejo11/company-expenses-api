package io.github.alexisTrejo11.company.expenses.dto.Expenses;

import io.github.alexisTrejo11.company.expenses.dto.Attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseCategory;
import io.github.alexisTrejo11.company.expenses.shared.enums.ExpenseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ExpenseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("category")
    private ExpenseCategory category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("receipt_url")
    private String receiptUrl;

    @JsonProperty("status")
    private ExpenseStatus status;

    @JsonProperty("approved_by_id")
    private Long approvedById;

    @JsonProperty("rejection_reason")
    private String rejectionReason;

    @JsonProperty("attachments")
    private List<AttachmentDTO> attachments;

}
