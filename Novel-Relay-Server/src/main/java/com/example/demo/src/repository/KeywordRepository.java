package com.example.demo.src.repository;

import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KEYWORD, Long> {

    @Query("select k " +
            "from KEYWORD k " +
            "where k.keyword = :keyword and k.novel.novel_id = :novel_id")
    KEYWORD findFreq(@Param("keyword")String keyword, @Param("novel_id")Long novel_id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update KEYWORD k " +
            "set k.freq_cnt = :freq " +
            "where k.keyword = :keyword and and k.novel_id = :novel_id ",nativeQuery = true )
    Integer updateFreq(@Param("freq")Integer freq,@Param("keyword")String keyword, @Param("novel_id")Long novel_id);


}
