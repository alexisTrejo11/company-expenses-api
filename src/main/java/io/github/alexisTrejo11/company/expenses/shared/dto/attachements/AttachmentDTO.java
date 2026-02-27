package io.github.alexisTrejo11.company.expenses.shared.dto.attachements;

import io.github.alexisTrejo11.company.expenses.shared.dto.expenses.ExpenseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttachmentDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("expense")
    private ExpenseDTO expense;

    @JsonProperty("attachment_url")
    private String attachmentUrl;

    @JsonProperty("uploaded_at")
    private LocalDateTime uploadedAt;
}
