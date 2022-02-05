package com.mingky.Board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.MemberType;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.repository.PostRepository;
import com.mingky.Board.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @LocalServerPort
    private int port;

    private MockMvc mvc;

    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PostService postService;


    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    public void tearDown() throws Exception {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 자우게시판_등록() throws Exception {

        Member member = Member.builder()
                .name("mingkyy")
                .email("test@test.com")
                .nickname("mi")
                .password(passwordEncoder.encode("12345"))
                .tel("01000000000")
                .memberType(MemberType.ROLE_MEMBER)
                .build();
        memberRepository.save(member);

        String title = "title";
        String content = "content";

        FreeWriteDto freeWriteDto = FreeWriteDto.builder()
                .title(title)
                .content(content)
                .category(Category.FREE)
                .member(member)
                .build();

        postService.freeSave(freeWriteDto);

        String url = "http://localhost:" + port + "/board/free/write";

        mvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


    }
}
