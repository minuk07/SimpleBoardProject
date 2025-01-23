package com.example.simple_board.board.db;

import com.example.simple_board.post.db.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    private String status;

    //1(Board) : N(PostEntity) 관계
    @OneToMany(
            mappedBy = "board" //PostEntity에 board가 존재해야 함.
    )
    private List<PostEntity> postList = List.of();
}
