package com.example.demo.src.repository;

import com.example.demo.src.entity.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<USER, Long> {

    @Query("select u " +
            "from USER u " +
            "where u.nickname = :nickname ")
    USER findByNickname(@Param("nickname") String nickname);

    @Query("select u " +
            "from USER u " +
            "where u.pw = :pw ")
    USER findByPw(@Param("pw") String pw);


}
