package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.MemberType;
import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;;
import com.mingky.Board.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @AfterEach
    public void cleanMember() throws Exception{
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입_테스트() throws Exception{
        String name = "김민경";
        String password = "1234";
        String nickname = "mingkyy";
        String email = "mingkyy12@gmail.com";

        SignupDto signupDto = SignupDto.builder()
                .name(name)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();

        String url = "http://localhost:"+port+"/signup";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, signupDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getEmail()).isEqualTo(email);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(passwordEncoder.matches(password, all.get(0).getPassword()));
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
    }



    @Test
    public void 로그인_테스트() throws Exception{
        String name = "test";
        String username = "test@naver.com";
        String password = "12345";
        String nickname = "mingkyy";

        SignupDto signupDto = new SignupDto(username, name, password, nickname);
        memberService.save(signupDto);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("username=test@naver.com&password=12345"))
                .andExpect(status().isFound())
                .andExpect(header().stringValues(HttpHeaders.LOCATION, "/"));

    }



}
