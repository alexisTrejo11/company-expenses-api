package io.github.alexisTrejo11.company.expenses.mapper;

import io.github.alexisTrejo11.company.expenses.dto.Attachements.AttachmentDTO;
import io.github.alexisTrejo11.company.expenses.model.ExpenseAttachment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T18:43:16-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.12 (JetBrains s.r.o.)"
)
@Component
public class AttachmentMapperImpl implements AttachmentMapper {

    @Override
    public AttachmentDTO entityToDTO(ExpenseAttachment expenseAttachment) {
        if ( expenseAttachment == null ) {
            return null;
        }

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setId( expenseAttachment.getId() );
        attachmentDTO.setAttachmentUrl( expenseAttachment.getAttachmentUrl() );
        attachmentDTO.setUploadedAt( expenseAttachment.getUploadedAt() );

        return attachmentDTO;
    }
}
