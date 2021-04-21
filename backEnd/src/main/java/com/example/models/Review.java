package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "feedbacks", schema = "public")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "feedbacks_id_feedback_seq")
    @Column(name="id_feedback")
    private int idFeedback;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_author")
    private User user;

    @Column(name="feedback_content")
    private String comment;
    @Column(name="feedback_rating")
    private int rating;

    public Review(User user, String comment, int rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }
}
