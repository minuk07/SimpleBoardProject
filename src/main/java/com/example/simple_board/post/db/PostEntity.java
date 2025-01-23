package com.example.simple_board.post.db;

import com.example.simple_board.board.db.BoardEntity;
import com.example.simple_board.reply.db.ReplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long boardId;
    @ManyToOne //BoardEntity가 N이고 상대방이 1 변수 뒤에다가 자동으로 _id를 붙임.
    @JsonIgnore //무한반복에서 빠져나오기 위해 한쪽에서 연결을 끊음.
    private BoardEntity board; //객체 지향으로 접근 => board_id

    private String userName;

    private String password;

    private String email;

    private String status;

    private String title;

    @Column(columnDefinition = "TEXT") //text type
    private String content;

    private LocalDateTime postedAt;

    @Transient //데이터베이스의 컬럼으로 사용하지 않겠다.
    private List<ReplyEntity> replyList = List.of(); //빈 리스트가 디폴트

}
