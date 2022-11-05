package com.example.demo.src.repository;

import com.example.demo.src.entity.NOVEL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends JpaRepository<NOVEL, Long>{

    NOVEL findByNovelId(Long novel_id);
}
