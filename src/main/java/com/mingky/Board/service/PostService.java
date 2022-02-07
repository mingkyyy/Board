package com.mingky.Board.service;

import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long freeSave(FreeWriteDto freeWriteDto) {
        return postRepository.save(freeWriteDto.freeWrite()).getId();
    }



    public Page<Post> findCategoryPage(Category free, Pageable pageable) {
        return postRepository.findByCategory(free, pageable);
    }

    public Post findRead(long id) {
        return postRepository.findById(id).orElseThrow();
    }
}
