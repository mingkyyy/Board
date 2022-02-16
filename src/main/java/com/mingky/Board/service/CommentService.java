package com.mingky.Board.service;


import com.mingky.Board.domain.Comment;
import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.CommentDto;
import com.mingky.Board.repository.CommentRepository;
import com.mingky.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Long saveComment(CommentDto commentDto, Long id, Member member) {

        Post post = postRepository.findById(id).orElseThrow();
        commentRepository.save(Comment.builder()
                        .comment(commentDto.getComment())
                        .commentWriter(member)
                        .post(post)
                .build());
        return id;
    }


    public Long deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
        return id;
    }
}
