package com.example.demo.src.repository;

import com.example.demo.src.dto.response.GetNovelIdRes;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RelayRepository extends JpaRepository<RELAY, Long>{

    @Query ("select r, n " +
            "from RELAY r, NOVEL n " +
            "where r.novel.novel_id = :novel_id and n.novel_id = :novel_id")
    List<GetNovelIdRes> findByNovelIdInGroup(Long novel_id);

}

