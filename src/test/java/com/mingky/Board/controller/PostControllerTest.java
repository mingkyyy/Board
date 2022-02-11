package com.mingky.Board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingky.Board.domain.*;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.repository.PostRepository;
import com.mingky.Board.service.PostService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Member member = Member.builder()
                .name("mingkyy")
                .email("test@test.com")
                .nickname("mi")
                .password(passwordEncoder.encode("12345"))
                .tel("01000000000")
                .memberType(MemberType.ROLE_MEMBER)
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    public void tearDown() throws Exception {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Transactional
    public void 자우게시판_등록() throws Exception {


        String title = "title";
        String content = "content";

        List<Member> memberList = memberRepository.findAll();

        FreeWriteDto freeWriteDto = FreeWriteDto.builder()
                .title(title)
                .content(content)
                .category(Category.FREE)
                .member(memberList.get(0))
                .build();

        postService.freeSave(freeWriteDto);

        String url = "http://localhost:" + port + "/free/write";


        mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule())
                        .writeValueAsString(freeWriteDto))
        ).andExpect(status().isOk());

        List<Post> posts = postRepository.findAll();
        Post post = posts.get(posts.size() - 1);

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @Transactional
    public void 게시물_삭제() throws Exception {
        List<Member> memberList = memberRepository.findAll();

        Post post = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .category(Category.FREE)
                .build());

        post.setWrite(memberList.get(0));
        postRepository.save(post);

        Long id = post.getId();

        String url = "http://localhost:" + port + "/board/free/read/" + id;

        HttpEntity<Post> savedEntity = new HttpEntity<>(post);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, savedEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);


    }
}
