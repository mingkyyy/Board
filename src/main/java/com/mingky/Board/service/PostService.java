package com.mingky.Board.service;

import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long freeSave(FreeWriteDto freeWriteDto) {
        return postRepository.save(freeWriteDto.freeWrite()).getId();
    }
}
