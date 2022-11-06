package com.example.demo.src.repository;

import com.example.demo.src.dto.response.GetNovelIdRes;
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
<<<<<<< HEAD
            "where r.novel.novel_id = :novel_id ")
    List<RELAY> findByNovelIdInGroup(Long novel_id);
=======
            "where r.novel.novel_id = :novel_id")
    List<RELAY> findByNovelIdInGroup(@Param("novel_id") Long novel_id);
>>>>>>> 37a24da51eaaad13c0e4102bb2aa47d2e59b5bdd

}

