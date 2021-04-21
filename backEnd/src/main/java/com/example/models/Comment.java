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
}
