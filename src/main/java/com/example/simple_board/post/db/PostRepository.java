package com.example.simple_board.post.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    //쿼리 메소드 구현
    //select * from post where id = ? and status = ? order by id desc limit 1
    //id가 ?고 status가 ?인 것들만 정렬해서 보여줘라
    Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String Status);
}
