package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.entity.LIKEINFO;
import com.example.demo.src.repository.LikeInfoRepository;
import com.example.demo.src.repository.NovelRepository;
import com.example.demo.src.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class LikeInfoService {

    private LikeInfoRepository likeInfoRepository;
    private UserRepository userRepository;
    private NovelRepository novelRepository;

    @Autowired
    public LikeInfoService(LikeInfoRepository likeInfoRepository, UserRepository userRepository, NovelRepository novelRepository) {
        this.likeInfoRepository = likeInfoRepository;
        this.userRepository = userRepository;
        this.novelRepository = novelRepository;
    }



    public LIKEINFO findLIKEINFO(Long user_id, Long novel_id) throws BaseException {
        LIKEINFO find = likeInfoRepository.findLIKEINFO(user_id, novel_id);
        return find;


    }

    //없으면 새로 생성해야지. 생성했을때 active true임 눌렀으니깐.
    public LIKEINFO createLIKEINFO(Long user_id, Long novel_id) throws BaseException {
        try{
            LIKEINFO nl = LIKEINFO.builder()
                    .likeinfo_active(true)
                    .novel(novelRepository.getOne(novel_id))
                    .user(userRepository.getOne(user_id))
                    .build();
            LIKEINFO saved = likeInfoRepository.save(nl);
            System.out.println("저장되었다. " + saved.toString());
            return saved;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public Integer changeActive(boolean likeinfo_active, Long user_id, Long novel_id){
        Integer check = likeInfoRepository.changeActive(likeinfo_active,user_id,novel_id);

        return check;

    }

    public boolean getPresentLike(Long novel_id, Long user_id){
        boolean check = likeInfoRepository.getPresentLike(novel_id, user_id);

        return check;

    }



}
