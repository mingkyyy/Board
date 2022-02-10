package com.mingky.Board.domain;


import javax.persistence.*;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Member write;

    @ManyToMany(mappedBy = "like", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Member> likers;

   @Enumerated(EnumType.STRING)
    private Category category;

    private int report;

    @Column
    private int hit;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @PostLoad
    public void createList() {
        if (comments == null) comments = new ArrayList<>();
        if (likers == null) likers = new ArrayList<>();
    }


}
