package com.example.demo.src.service;


import com.example.demo.config.BaseException;
import com.example.demo.src.dto.request.PostSignUpReq;
import com.example.demo.src.dto.request.PostSigninReq;
import com.example.demo.src.dto.response.PostSignUpRes;
import com.example.demo.src.dto.response.PostSigninRes;
import com.example.demo.src.entity.USER;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
@Transactional
public class UserService {


    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService)
    {
        this.userRepository = userRepository;
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
            // TODO: jwt는 다음주차에서 배울 내용입니다!

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
        USER user = userRepository.findByNickname(postSigninReq.getNickname());
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
        }



    }




}