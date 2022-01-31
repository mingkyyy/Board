package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

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
        assertThat(all.get(0).getPassword()).isEqualTo(password);
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
    }
}
