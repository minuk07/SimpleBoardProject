package com.example.simple_board.post.controller;

import com.example.simple_board.board.model.BoardRequest;
import com.example.simple_board.post.db.PostEntity;
import com.example.simple_board.post.model.PostRequest;
import com.example.simple_board.post.model.PostViewRequest;
import com.example.simple_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor //자동 생성자 오버로딩
public class PostApiController {

    private final PostService postService; //controller는 service와 상호작용.

    @PostMapping("")
    public PostEntity create( //게시글 생성
        @Valid //postRequest의 형식에 맞는지 검증.
        @RequestBody //Json body로 요청을 받겠다는 어노테이션
        PostRequest postRequest
    ){
        return postService.create(postRequest);
    }

    @PostMapping("/view") //클릭하면 비밀번호를 입력해야 볼 수 있으므로 단순한 get으로는 불가능.
    public PostEntity view(
        @Valid
        @RequestBody PostViewRequest postViewRequest
    ){
        return postService.view(postViewRequest);
    }

    @GetMapping("/all")
    public List<PostEntity> list(

    ){
        return postService.all();
    }

    @PostMapping("/delete") //비밀번호를 넣어야 하기 때문에 단순 deleteMapping으로 불가능.
    public void delete(
            @Valid
            @RequestBody PostViewRequest postViewRequest
    ){
        postService.delete(postViewRequest);
    }
}
