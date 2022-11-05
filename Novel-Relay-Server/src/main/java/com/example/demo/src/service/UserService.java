package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.entity.USER;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class UserService {


    private UserRepository userRepository;

    @Autowired

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public PostSignUpRes signup(PostSignUpReq postSignUpReq) throws BaseException {

        // id 중복 확인
        try{
            USER user = userRepository.findByUserIdx(postSignUpReq.getUser_id());
            if(user.getUser_id() == postSignUpReq.getUser_id()){ //존재한다. 즉, 중복
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
//            USER u = userRepository.save(postSignUpReq);
//            //jwt 발급.
//            // TODO: jwt는 다음주차에서 배울 내용입니다!
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }






    }




}
