package com.example.simple_board.post.controller;

import com.example.simple_board.board.model.BoardRequest;
import com.example.simple_board.post.db.PostEntity;
import com.example.simple_board.post.model.PostRequest;
import com.example.simple_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor //자동 생성자 오버로딩
public class PostApiController {

    private final PostService postService; //controller는 service와 상호작용.

    @PostMapping("")
    public PostEntity create(
        @Valid //postRequest의 형식에 맞는지 검증.
        @RequestBody //Json body로 요청을 받겠다는 어노테이션
        PostRequest postRequest
    ){
        return postService.create(postRequest);
    }
}
