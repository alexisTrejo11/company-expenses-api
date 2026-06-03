package io.github.alexisTrejo11.company.expenses.shared.mapper;

import io.github.alexisTrejo11.company.expenses.model.ExpenseAttachment;
import io.github.alexisTrejo11.company.expenses.shared.dto.attachements.AttachmentDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T13:31:57-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AttachmentMapperImpl implements AttachmentMapper {

    @Override
    public AttachmentDTO entityToDTO(ExpenseAttachment expenseAttachment) {
        if ( expenseAttachment == null ) {
            return null;
        }

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setAttachmentUrl( expenseAttachment.getAttachmentUrl() );
        attachmentDTO.setId( expenseAttachment.getId() );
        attachmentDTO.setUploadedAt( expenseAttachment.getUploadedAt() );

        return attachmentDTO;
    }
}
