package com.example.simple_board.board.service;

import com.example.simple_board.board.db.BoardEntity;
import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.board.model.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService { //Repository와 연결

    private final BoardRepository boardRepository;

    public BoardEntity create( //BoardRequest 객체를 입력받아 BoardEntity 생성,저장.
            BoardRequest boardRequest
    ){
        var entity = BoardEntity.builder() //사용자가 요청한 이름으로 boardName 지정.
                .boardName(boardRequest.getBoardName())
                .status("REGISTERED") //게시판이 등록됐다는 상태
                .build();

        return boardRepository.save(entity);
    }
}
