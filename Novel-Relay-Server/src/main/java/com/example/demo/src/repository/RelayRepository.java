package com.example.demo.src.repository;

import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface RelayRepository extends JpaRepository<RELAY, Long>{

<<<<<<< HEAD
    @Query ("select r " +
            "from RELAY r " +
            "where r.novel.novel_id = :novel_id")
    ArrayList<RELAY> findByNovelIdInGroup(Long novel_id);

}
=======
}
>>>>>>> 0ad0a650fd73cc8cf1d6551e3553c4551e7e17c1
