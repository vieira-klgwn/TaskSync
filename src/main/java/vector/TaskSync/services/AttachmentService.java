package vector.TaskSync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.Attachment;
import vector.TaskSync.repositories.AttachmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    //create
    public Attachment createAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    //findAll
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    //findbyid
    public Optional<Attachment> getAttachmentById(long id) {
        return attachmentRepository.findById(id);
    }

    //update
    public Attachment updateAttachment(Long id,Attachment updatedAttachment) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            attachment.setId(updatedAttachment.getId());
            attachment.setFileName(updatedAttachment.getFileName());
            attachment.setFileUrl(updatedAttachment.getFileUrl());
            attachment.setTask(updatedAttachment.getTask());
            return attachmentRepository.save(attachment);
        }
        throw new RuntimeException("Attachment not found");
    }

    //delete
    public void deleteAttachmentById(long id) {
        attachmentRepository.deleteById(id);
    }
}
