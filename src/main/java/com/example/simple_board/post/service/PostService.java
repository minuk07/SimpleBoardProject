package com.example.simple_board.post.service;

import com.example.simple_board.board.db.BoardRepository;
import com.example.simple_board.common.Api;
import com.example.simple_board.common.Pagination;
import com.example.simple_board.post.db.PostEntity;
import com.example.simple_board.post.db.PostRepository;
import com.example.simple_board.post.model.PostRequest;
import com.example.simple_board.post.model.PostViewRequest;
import com.example.simple_board.reply.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository; //service느 controller와 repository(db)와 중간 계층 담당.

    private final BoardRepository boardRepository;

    private final ReplyService replyService;

    public PostEntity create(
            PostRequest postRequest
    ){
        var boardEntity = boardRepository.findById(postRequest.getBoardId()).get();

        var entity = PostEntity.builder()
                .board(boardEntity)
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .status("REGISTERED")
                .build();

        return postRepository.save(entity);
    }

    //1.게시글이 있는지 확인
    //2.비밀번호가 맞는지 확인
    public PostEntity view(@Valid PostViewRequest postViewRequest) {

        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(), "REGISTERED") //등록된 것만 select
                //postViewRequest 객체의 postId를 가져와 postRepository를 통해 데이터베이스에서 해당 id에 해당하는 게시글을 조회
                .map( it -> {
                    //해당 Entity가 존재할 때
                    if(!it.getPassword().equals(postViewRequest.getPassword())){ //요청된 password와 조회된 게시글의 password가 동일하지 않으면
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

//                    //답변글도 같이 게시
//                    var replyList = replyService.findAllByPostId(it.getId()); //postId에 해당하는 답변글들 리스트로 반환
//                    it.setReplyList(replyList);

                    return it; //비밀번호가 일치하면 해당 Entity 리턴
                }).orElseThrow( //Optional에 값이 비어 있는 경우(findById 결과가 비어있는 경우)
                        () -> {
                            return new RuntimeException("해당 게시글이 존재하지 않습니다. :" + postViewRequest.getPostId());
                        }
                );
    }

    public Api<List<PostEntity>> all(Pageable pageable) {
        var list = postRepository.findAll(pageable); //해당 페이지만큼 정보를 줌

        var pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements()) //해당 페이지에 있는 엘리먼트 개수
                .totalElements(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        var response = Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pagination(pagination)
                .build();

        return response;
    }

    public void delete(@Valid PostViewRequest postViewRequest) {
        postRepository.findById(postViewRequest.getPostId())
                //postViewRequest 객체의 postId를 가져와 postRepository를 통해 데이터베이스에서 해당 id에 해당하는 게시글을 조회
                .map( it -> {
                    //해당 Entity가 존재할 때
                    if(!it.getPassword().equals(postViewRequest.getPassword())){ //요청된 password와 조회된 게시글의 password가 동일하지 않으면
                        var format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    it.setStatus("UNREGISTRED");
                    postRepository.save(it);
                    return it; //비밀번호가 일치하면 해당 Entity 리턴
                }).orElseThrow( //Optional에 값이 비어 있는 경우(findById 결과가 비어있는 경우)
                        () -> {
                            return new RuntimeException("해당 게시글이 존재하지 않습니다. :" + postViewRequest.getPostId());
                        }
                );

    }
}
