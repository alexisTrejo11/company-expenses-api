package io.github.alexisTrejo11.construction.company.modules.expense.attachments.features.create;

import io.github.alexisTrejo11.construction.company.shared.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v2/api/expenses/{expenseId}/attachments")
@RequiredArgsConstructor
@Tag(name = "Expenses", description = "Construction expense tracking")
public class CreateExpenseAttachmentController {
  private final CreateExpenseAttachmentHandler handler;

  @PostMapping
  @Operation(summary = "Upload expense attachment", description = "Uploads and links a receipt or invoice (PDF/XML/image) to the expense.")
  public ResponseEntity<ResponseWrapper<?>> upload(
      @PathVariable Long expenseId,
      @RequestParam("file") MultipartFile file) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ResponseWrapper.created(handler.execute(expenseId, file), "ExpenseAttachment"));
  }
}
