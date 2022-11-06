package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.request.GetMyPageReq;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.request.PostSigninReq;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.entity.USER;
import com.example.demo.src.repository.KeywordRepository;
import com.example.demo.src.repository.NovelRepository;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static java.util.Objects.isNull;


@Service
@Transactional
public class UserService {


    private UserRepository userRepository;
    private NovelRepository novelRepository;
    private JwtService jwtService;
    private KeywordRepository keywordRepository;


    @Autowired
    public UserService(KeywordRepository keywordRepository, UserRepository userRepository, NovelRepository novelRepository, JwtService jwtService)
    {
        this.keywordRepository = keywordRepository;
        this.userRepository = userRepository;
        this.novelRepository = novelRepository;
        this.jwtService = jwtService;
    }

    public PostSignUpRes signup(PostSignUpReq postSignUpReq) throws BaseException {

        // id 중복 확인
        try{
            USER user = userRepository.findByNickname(postSignUpReq.getNickname());
            if(postSignUpReq.getNickname().equals(user.getNickname())){ //존재한다. 즉, 중복

                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //존재안함.

        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postSignUpReq.getPw());
            postSignUpReq.setPw(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            USER saved = USER.builder().
                    nickname(postSignUpReq.getNickname())
                    .pw(postSignUpReq.getPw())
                    .build();
            USER saveuser = userRepository.save(saved);
            //jwt 발급.

            String jwt = jwtService.createJwt(saveuser.getUser_id());
            return new PostSignUpRes(saveuser.getUser_id(),jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }




    }


    public PostSigninRes signin(PostSigninReq postSigninReq) throws BaseException{


        System.out.println("들어와?");
        System.out.println(postSigninReq.getNickname());
        System.out.println(postSigninReq.getPw());
        //클라이언트에서 받은 객체에서 email을 받아서, 이 email을 가진 해당 유저를 리턴을 해줌.
        // (DB에서 회원가입이 되어 있는지를 확인하면서 가지고 오는거임)

        System.out.println("들어와?");
        System.out.println(postSigninReq.getNickname());
        System.out.println(postSigninReq.getPw());
        //클라이언트에서 받은 객체에서 email을 받아서, 이 email을 가진 해당 유저를 리턴을 해줌.
        // (DB에서 회원가입이 되어 있는지를 확인하면서 가지고 오는거임)

        USER user = userRepository.findByNickname(postSigninReq.getNickname());
        if (isNull(user)) {
            return new PostSigninRes(-1L, "-1");
        }

        System.out.println(user.toString());
        String encryptPwd;

        try{

            //클라이언트에서 입력한 비밀번호를 암호함.
            encryptPwd = new SHA256().encrypt(postSigninReq.getPw());

        } catch (Exception exception) {

            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);

        }

        System.out.println("DB: " +user.getPw());
        System.out.println("enc : " + encryptPwd);
        //DB에 저장되어 있는 비밀번호와 일치히는지를 확인함.
        if(user.getPw().equals(encryptPwd)){
            //일치하면 해당 userIdx 를 받아옴
            long userIdx = user.getUser_id();
            //userIdx를 이용해서 jwt 토큰 생성
            String jwt = jwtService.createJwt(userIdx);

            return new PostSigninRes(userIdx, jwt);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
            //return new PostSigninRes()
        }

    }

    public List<List> getMyNovelGroup(GetMyPageReq getMyPageReq) throws BaseException {


//        int userIdxByJwt = jwtService.getUserIdx();
//        if( getMyPageReq.getUser_id()!= userIdxByJwt){
//            throw new BaseException(INVALID_USER_JWT);
//        }

        //String nickname = userRepository.findByuserId(getMyPageReq.getUser_id());

        Long user_id = getMyPageReq.getUser_id();

        List<NOVEL> writeNovelGroup = novelRepository.findByUserIdInWrite(user_id);
        List<GetMyPageRes> getWriteNovelList = new ArrayList<>();

        if (writeNovelGroup.isEmpty()) {

        } else {
            for (NOVEL writeNovel: writeNovelGroup) {
                List<String> keywords = keywordRepository.findKEYWORDLIST(writeNovel.getNovel_id());

                GetMyPageRes getMyPageRes = GetMyPageRes.builder()
                        .novel_id(writeNovel.getNovel_id())
                        .category(writeNovel.getCategory())
                        .max_num(writeNovel.getMax_num())
                        .n_content(writeNovel.getN_content())
                        .like_count(writeNovel.getLike_count())
                        .relay_count(writeNovel.getRelay_count())
                        .active(writeNovel.isActive())
                        .keywords(keywords)
                        .build();
                getWriteNovelList.add(getMyPageRes);
            }
        }


        List<NOVEL> participateNovelGroup = novelRepository.findByUserIdInParticipate(user_id);
        List<GetMyPageRes> getParticipateNovelList = new ArrayList<>();

        if (participateNovelGroup.isEmpty()) {

        } else {
            for (NOVEL participateNovel: participateNovelGroup) {

                List<String> keywords = keywordRepository.findKEYWORDLIST(participateNovel.getNovel_id());


                GetMyPageRes getMyPageRes = GetMyPageRes.builder()
                        .novel_id(participateNovel.getNovel_id())
                        .category(participateNovel.getCategory())
                        .max_num(participateNovel.getMax_num())
                        .n_content(participateNovel.getN_content())
                        .like_count(participateNovel.getLike_count())
                        .relay_count(participateNovel.getRelay_count())
                        .active(participateNovel.isActive())
                        .keywords(keywords)
                        .build();
                getParticipateNovelList.add(getMyPageRes);
            }
        }

        List<NOVEL> likeNovelGroup = novelRepository.findByUserIdInLike(user_id);
        List<GetMyPageRes> getLikeNovelList = new ArrayList<>();

        if (likeNovelGroup.isEmpty()) {

        } else {
            for (NOVEL likeNovel: likeNovelGroup) {

                List<String> keywords = keywordRepository.findKEYWORDLIST(likeNovel.getNovel_id());


                GetMyPageRes getMyPageRes = GetMyPageRes.builder()
                        .novel_id(likeNovel.getNovel_id())
                        .category(likeNovel.getCategory())
                        .max_num(likeNovel.getMax_num())
                        .n_content(likeNovel.getN_content())
                        .like_count(likeNovel.getLike_count())
                        .relay_count(likeNovel.getRelay_count())
                        .active(likeNovel.isActive())
                        .keywords(keywords)
                        .build();
                getLikeNovelList.add(getMyPageRes);
            }
        }

        List<List> myPageList = new ArrayList<>();
        myPageList.add(getWriteNovelList);
        myPageList.add(getParticipateNovelList);
        myPageList.add(getLikeNovelList);


        return  myPageList;
    }

}