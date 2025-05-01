package vector.TaskSync.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Comment;
import vector.TaskSync.models.CommentDTO;
import vector.TaskSync.models.UserDTO;
import vector.TaskSync.services.CommentService;
import vector.TaskSync.services.TeamAccessService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final TeamAccessService teamAccessService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'TEAM_LEAD')")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.saveComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'TEAM_LEAD')")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments().stream()
                .filter(comment -> comment.getTask().getTeam() == null || teamAccessService.isUserInTeam(comment.getTask().getTeam().getId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@teamAccessService.isUserInTeam(#id)")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(comment -> new ResponseEntity<Comment>(comment,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")

    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
//            if ((SecurityContextHolder.getContext().getAuthentication().getName()) == (commentService.getCommentById(id).get().getAuthor().toString())) {
//
//
//            }
            Comment updatedComment = commentService.updateComment(id, comment);
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tasks/{taskId}/comment")
    @PreAuthorize("hasAnyRole('USER','TEAM_LEAD')")
    public ResponseEntity<CommentDTO> createCommentForTask(
            @PathVariable("taskId") @Positive Long taskId,
            @Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        Comment createdComment = commentService.createCommentForTask(taskId, comment);
        return new ResponseEntity<>(mapToCommentDTO(createdComment), HttpStatus.CREATED);
    }

    private Comment mapToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        return comment;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedDate(comment.getCreatedDate());

        if (comment.getAuthor() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(comment.getAuthor().getId());
            userDTO.setFirstName(comment.getAuthor().getFirstName());
            userDTO.setLastName(comment.getAuthor().getLastName());
            commentDTO.setAuthor(userDTO);
        }

        if (comment.getTask() != null) {
            commentDTO.setTaskId(comment.getTask().getId());
        }

        return commentDTO;
    }



    @GetMapping("/tasks/{taskId}/comments")
    @PreAuthorize("hasAnyRole('USER', 'TEAM_LEAD')")
    public ResponseEntity<List<Comment>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }
}



