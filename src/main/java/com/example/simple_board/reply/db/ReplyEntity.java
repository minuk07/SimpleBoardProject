package com.example.simple_board.reply.db;
import com.example.simple_board.post.db.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long postId;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private PostEntity post; // post + _id


    private String userName;

    private String password;

    private String status;

    private String title;

    @Column(columnDefinition = "TEXT") //text type
    private String content;

    private LocalDateTime repliedAt;
}
