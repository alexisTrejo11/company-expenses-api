package io.github.alexisTrejo11.company.expenses.service.Controller;

import io.github.alexisTrejo11.company.expenses.controller.ExpenseAttachmentController;
import io.github.alexisTrejo11.company.expenses.dto.Attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.dto.Expenses.ExpenseDTO;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.AttachmentService;
import io.github.alexisTrejo11.company.expenses.service.Interfaces.ExpenseService;
import io.github.alexisTrejo11.company.expenses.shared.file.FileHandler;
import io.github.alexisTrejo11.company.expenses.shared.MessageGenerator;
import io.github.alexisTrejo11.company.expenses.shared.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExpenseAttachmentControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private AttachmentService attachmentService;

    @Mock
    private FileHandler fileHandler;

    @Mock
    private MessageGenerator message;

    @InjectMocks
    private ExpenseAttachmentController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void addAttachment_Success() throws Exception {
        Long expenseId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test Content".getBytes());

        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(expenseId);

        Result<String> fileUrlResult = Result.success("http://example.com/file/test.txt");

        when(expenseService.getExpenseById(expenseId)).thenReturn(expenseDTO);
        when(fileHandler.uploadAttachmentFile(any(), any())).thenReturn(fileUrlResult);

        mockMvc.perform(multipart("/v1/api/employees/expenses/{expenseId}/attachments", expenseId)
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Attachment Successfully Added to Expense With Id [1]"));
    }

    @Test
    void addAttachment_ExpenseNotFound() throws Exception {
        Long expenseId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test Content".getBytes());

        when(expenseService.getExpenseById(expenseId)).thenReturn(null);

        mockMvc.perform(multipart("/v1/api/employees/expenses/{expenseId}/attachments", expenseId)
                        .file(file))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Expense with ID [1] not found"));
    }

    // Not Working
    @Test
    void getAttachmentsByExpenseId_Success() throws Exception {
        Long expenseId = 1L;

        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(1L);
        attachmentDTO.setAttachmentUrl("http://example.com/file/test.txt");
        List<AttachmentDTO> attachments = Collections.singletonList(attachmentDTO);

        when(attachmentService.getAttachmentsByExpenseId(expenseId)).thenReturn(attachments);

        mockMvc.perform(get("/v1/api/employees/expenses/{expenseId}/attachments", expenseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].url").value("http://example.com/file/test.txt"));
    }

    @Test
    void getAttachmentsByExpenseId_NotFound() throws Exception {
        Long expenseId = 1L;

        when(attachmentService.getAttachmentsByExpenseId(expenseId)).thenReturn(null);

        mockMvc.perform(get("/v1/api/employees/expenses/{expenseId}/attachments", expenseId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Expense with expenseId [1] not found"));
    }
}

