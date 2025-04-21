package vector.TaskSync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.Comment;
import vector.TaskSync.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    //create
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    //findAll
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    //findbyid
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    //update
    public Comment updateComment(Long id,Comment updatedComment) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setId(updatedComment.getId());
            comment.setAuthor(updatedComment.getAuthor());
            comment.setTask(updatedComment.getTask());
            comment.setContent(updatedComment.getContent());
            comment.setCreatedAt(updatedComment.getCreatedAt());
            return commentRepository.save(comment);
        }
        throw new RuntimeException("Comment not found");

    }

    //delete
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);

    }
}
