package com.example.demo.src.repository;

import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RelayRepository extends JpaRepository<RELAY, Long>{

    @Query ("select r " +
            "from RELAY r " +
            "where r.novel.novel_id = :novel_id")
    List<RELAY> findByNovelIdInGroup(@Param("novel_id") Long novel_id);

}

