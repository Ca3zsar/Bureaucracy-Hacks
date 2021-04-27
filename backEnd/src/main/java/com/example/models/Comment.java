package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "comments_id_comment_seq")
    @Column(name="id_comment")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_author")
    private User user;
    @Column(name="comment_content")
    private String comment;
    @Column(name="bureaucratic_process")
    private String process;
    @Column(name="q_1")
    private Boolean q1;
    @Column(name="q_2")
    private Integer q2;
    @Column(name="q_3")
    private String q3;
    @Column(name="q_4")
    private Integer q4;
    @Column(name="show")
    private Boolean show;
}
