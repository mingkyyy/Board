package com.mingky.Board.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String tel;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @JsonManagedReference
    @OneToMany(mappedBy = "write", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> like;

    @PostLoad
    public void createList(){
        if (posts == null) posts = new ArrayList<>();
        if (like == null) like = new ArrayList<>();
    }

    public void addLike(Post post) {
        like.add(post);
    }

}
