# UniD-Hackathon-Team12-backend

## 🪄서비스 소개
`MZ 세대`를 위한 릴레이 소설 웹페이지
- 본 서비스는 2022 Uni-DTHON 위해 제작되었습니다.
- 여러 사람이 하나의 주제나 스토리 흐름에 맞게 글을 이어서 써 내려갈 수 있도록 하는 서비스입니다. 

[Presentation Slides](https://docs.google.com/presentation/d/1GxK5zKomLM1_FnuK0V7ZoemmqCHrX4uL/edit#slide=id.p1) / [Demo Video](https://drive.google.com/file/d/1OwYN68E1gEp2z1kU-6wfbcV4gsC4XWtN/view?usp=sharing)


<br>

## ⚙️ 시현방법
```bash
 git clone https://github.com/UniD-Hackathon-Team12/UniD-Hackathon-Team12-backend.git
```

### 시작하기
```bash
 cd
 ./gradlew build clean
 cd ./build/libs
 java -jar demo-0.0.1-SNAPSHOT.jar
```
<br>

## 👨‍💻 백엔드 측 개발 참여자 및 역활
- 김선우 (성균관대학교)
  - 데이터 베이스 개념적 설계 & 논리적 설계
  - user/mypage API 구현
  - novel/all API 구현
  - novel/part/:challenge API 구현
  - novel/:novelid API 구현
  - novel/:novelid/relay API 구현


<br>


## 📌주요 기능 

<table>
  <tr>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440257-7fcf199c-e3f1-4a91-a187-ebfbe3202d36.png"></td>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440324-3349ac40-c1d6-4227-a9d8-89bf205fbe65.png"></td>
  </tr>
  <tr>
    <td align="center"><b>스플래시</b></td>
    <td align="center"><b>습관 탭</b></td>
  </tr>
</table>
<table>
  <tr>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440346-f450f8b0-e370-4bb2-9761-6a7b996559e5.png"></td>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440395-52be145b-b78f-4fcf-904c-799f3c6cd51d.png"></td>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440433-38968a1a-5f1f-49f2-886d-87ca1ebb182c.png"></td> 
  </tr>
  <tr>
    <td align="center"><b>챌린지 탭</b></td>
    <td align="center"><b>챌린지 상세</b></td>
    <td align="center"><b>챌린지 참여</b></td>
  </tr>
</table>
<table>
  <tr>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440522-72934043-f3df-4620-8f82-6e4cdf1de382.png"></td>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440599-338416c1-cdb2-4b4b-8d0b-cd14bd9b196c.png"></td>
  </tr>
  <tr>
    <td align="center"><b>마켓 탭</b></td>
    <td align="center"><b>마켓 상품 등록</b></td>
  </tr>
</table> 
<table>
  <tr>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175441283-db777079-4e5c-4033-8272-ffc5505a43e7.png"></td>
    <td><img width="200" src="https://user-images.githubusercontent.com/63237214/175440669-329eb6b3-925f-42b4-9ae6-30f3b08c78cf.png"></td>
  </tr>
  <tr>
     <td align="center"><b>마이페이지</b></td>
     <td align="center"><b>챌린지 참여 내역</b></td>
  </tr>
</table>
  
  <br>

## 💻 Tech Stack
<table class="tg">
<tbody>
  <tr>
    <td><b>BackEnd</b></td>
        <td>
          Spring Boot
        </td>
  </tr>
<tr>

<td><b>Cloud</b></td>
<td>
  //
<br>
  <strong>//<strong>
</td>
<tr>
    <td><b>Strategy</b></td>
<td>RESTful API</td>
</tr>
<tr>
    <td><b>Other Tool</b></td>
<td>Notion, Slack</td>
</tr>
</tbody>
</table>
<br>

## 👍 시스템 구조
![iShot_2022-06-27_15 15 47](https://user-images.githubusercontent.com/72367040/175872280-9e81691d-775a-449a-9f46-dc8681552c03.jpg)

<br>

## 🗂 Floder Map
```bash
* 📦 Novel-Relay-Server
  ├──  .gradle
  ├──  gradle/wrapper
  ├──  logs
  ├──  out/production
  ├──  src
  │   ├──  test/java/com/example/demo
  │   ├──  main
  │   │   ├──  java/com/example/demo
  │   │   │   ├──  config
  │   │   │   ├──  src
  │   │   │   │   ├──  controller
  │   │   │   │   ├──  dto
  │   │   │   │   ├──  entity
  │   │   │   │   ├──  repository
  │   │   │   │   ├──  service
  │   │   │   │   └──  WebSecurityConfig.java
  │   │   │   ├──  utils
  │   │   │   └──  DemoApplication.java
  │   │   └──  resources
  ├──  .gitignore
  ├──  build.gradle
  ├──  gradlew
  ├──  gradlew.bat
  └──  setting.gradle
```

<br>

## ⚙️ 개발 환경
### 1. Software requirement
- IntelliJ + Spring Boot

