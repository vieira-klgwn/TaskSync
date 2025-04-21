package vector.TaskSync.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Attachment;
import vector.TaskSync.services.AttachmentService;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        Attachment createdAttachment = attachmentService.createAttachment(attachment);
        return new ResponseEntity<>(createdAttachment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Attachment>> getAllAttachments() {
        return ResponseEntity.ok(attachmentService.getAllAttachments());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable("id") Long id) {
        return attachmentService.getAttachmentById(id)
                .map(attachment -> new ResponseEntity<>(attachment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id,@Valid @RequestBody Attachment attachment) {
        try{
            Attachment updatedAttachment = attachmentService.updateAttachment(id, attachment);
            return new ResponseEntity<>(updatedAttachment, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Attachment> deleteAttachment(@PathVariable Long id) {
        try {
            attachmentService.deleteAttachmentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
