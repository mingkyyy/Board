package com.mingky.Board.service;

import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

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



    @Transactional
    public String boardLike(Member member, Long id) {
        member = memberRepository.findById(member.getId()).orElseThrow();
        Post post = postRepository.findById(id).orElseThrow();
        if (member.getLike().contains(post) == true){
            member.getLike().remove(post);
            return "deleteLike";
        }else {
            member.addLike(post);
            return "addLike";
        }
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }
}
