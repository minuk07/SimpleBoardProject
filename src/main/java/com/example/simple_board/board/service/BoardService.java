package com.example.simple_board.board.service;

import com.example.simple_board.board.db.BoardEntity;
import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.board.model.BoardDTO;
import com.example.simple_board.board.model.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService { //Repository와 연결

    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;

    public BoardDTO create( //BoardRequest 객체를 입력받아 BoardEntity 생성,저장.
            BoardRequest boardRequest
    ){
        var entity = BoardEntity.builder() //사용자가 요청한 이름으로 boardName 지정.
                .boardName(boardRequest.getBoardName())
                .status("REGISTERED") //게시판이 등록됐다는 상태
                .build();

        var saveEntity = boardRepository.save(entity);
        return boardConverter.toDto(saveEntity);
    }

    public BoardDTO view(Long id) {
        var entity = boardRepository.findById(id).get(); //있다고 가정
        return boardConverter.toDto(entity); //DTO로 바꿔서 전달
    }
}
