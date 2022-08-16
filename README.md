# 꿈꿔왔던 사이드 프로젝트가 시작되는 곳, Coco!
![CocoBanner2](https://user-images.githubusercontent.com/103922744/180134172-04dda2fd-bebd-48ef-9a1c-77ae638b4979.jpg)
<br>
<br>

## 1. 프로젝트 개요
- **제작기간**: 2022.06.24 ~ 2022.07.29 <br>
- **참여인원**: 3명 <br>
- **주제**: 사이드 프로젝트 구인 플랫폼 <br>
- **기획의도**: 사이드 프로젝트를 직접 기획하고 함께할 팀원을 모집할 수 있는 플랫폼
<br>
<br>

## 2. 사용 기술

- **Backend**: Java 11 / Spring Boot 2.7.1 / Gradle 7.4.1 / Spring Data JPA / Spring Security / Java JWT
- **Database**: AWS RDS (MySQL 8.0.28)
- **Infra**: AWS (ELB, EC2, S3, CloudFront)
- **CI/CD**: Github Action
- **Frontend**: HTML5 / CSS3 / JavaScript /Bulma / JQuery / Webpack / Node.js
<br>
<br>

## 3. ERD 설계
<img width="948" alt="COCO ERD" src="https://user-images.githubusercontent.com/103922738/184827139-36cd7ddc-4731-4c82-b19d-716d3fd73a34.png">
<br>
<br>

## 4. 아키텍쳐 설계
<img width="948" alt="아키텍처" src="https://user-images.githubusercontent.com/103922738/183818487-3a97b9b8-86ba-4879-99c4-4f57105e918f.png">


## 5. 구현한 기능
| 카테고리 |                기능                 | 기능 내용                                                       |
| :------: |:---------------------------------:|:------------------------------------------------------------|
| 프로필 | D | 회원 탈퇴 |
| 게시글 | R | 글 작성 유저 프로필 보기 </br> 글 작성 유저에게 쪽지 보내기 </br> 게시글 조회수 |
| 쪽지 | CRD | 쪽지 보내기 </br> 쪽지 답장 보내기 </br>보낸&받은 쪽지리스트 </br> 쪽지 상세보기 </br> 쪽지 삭제 |
| 북마크 | CRD | 게시글 북마크에 저장 </br> 북마크 리스트 획득 </br> 북마크 삭제|
<br>
<br>

## 6. 기억에 남는 기능
<details>
<summary> 쪽지 상세읽기 </summary>
<div markdown="1">
https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/MessageService.java#L73-L105

### 기억에 남는 이유
- 초기에 쪽지를 보낼 때 발신 & 수신 유저를 이메일로 구별했는데, 유저의 입장에서 닉네임을 이용하는 것이 접근성과 사용성이 편하다고 판단하여 닉네임으로 쪽지발신과 수신을 하게끔 변경했던 기능입니다. 이는 프로젝트에서 처음으로 사용자 친화적으로 생각하여 개선시켰던 경험이기 때문에 기억에 남았습니다.<br>
또한, 기존에 이메일으로 주고받았던 쪽지를 닉네임으로 변경하면 DB에 저장되어있던 데이터도 자동으로 변경되는지 궁금했는데, 바뀌는 것을 처음 확인하여 Dto의 역할을 다시 생각할 수 있었습니다.
- 기존에 받은 쪽지들만 확인할 수 있었지만 고객 피드백에서 '보낸 쪽지도 확인할 수 있으면 좋겠다'라는 의견이 있었고, 좋은 피드백이였기에 수용하여 기능을 구현을 했습니다.
처음에는 Service에서 보낸 쪽지들을 불러오는 메소드를 한개 더 만드려고 했었는데, 비효율적이라 생각했고 조금 더 나은 방법을 고민하던 중에 Member Entity에서 기존에 선언해준 받은 메시지만 찾는 [findMessage](https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/domain/Member.java#L135-L151) 메소드에 보낸 쪽지도 한번에 찾을 수 있도록 해서 효율적으로 코드 작성할 수 있었습니다. <br>
- `읽음`, `읽지않음` 기능을 추가했습니다. 쪽지를 받은 유저(수신자)가 쪽지를 읽으면 본인 & 쪽지를 보낸 유저(발신자) 모두 쪽지를 읽었는지 확인할 수 있도록 사용자의 편의성을 생각하여 개발했습니다. 보낸사람이 본인이 보낸 쪽지를 읽어도 쪽지의 상태는 변경되지 않습니다.<br>
- 같은 status code여도 status message를 [다르게 부여](https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/MessageService.java#L38-L55)할 수 있고, 프론트엔드에도 이 부분을 연결해서 유저에게 백엔드의 status message에 따라서 alert를 다르게 보낼 수 있다는 것도 알게 됐습니다. <br>
- 처음으로 만들었던 기능인만큼 프로젝트의 처음부터 끝까지 계속해서 빌드업하고 수정 작업이 굉장히 많았기 때문에 가장 기억에 남습니다.<br>

</div><br><br>
</details>
<details>
<summary>북마크</summary>
https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/BookmarkService.java#L32-L69

### 기억에 남는 이유
- 처음으로 온전히 혼자서 만들어 본 북마크 기능입니다. 간단할 것 같았지만 구글 검색시에도 정보를 잘 찾을 수 없었고 Member & Post 두 개의 Entity와 연관 관계가 맺어져 있어서 어려웠기 때문에 기억에 남습니다. 
- 이미 저장한 북마크는 저장이 불가하도록 [예외처리](https://velog.io/@serringg/%EB%B6%81%EB%A7%88%ED%81%AC)를 하고, 한 개의 게시글에는 한 명의 유저만 저장되는 오류가 있어서 문제를 해결하면서 공부가 많이 됐습니다.
- 프로젝트 기간이 끝난 후에도 프로젝트 완성도를 높이기 위해 북마크 추가 기능을 구현하고 리팩토링하면서 완성했던 경험이 있어서 더 기억에 남습니다.

</details>
<br>
<br>

## 7. 핵심 트러블 슈팅

<details>
<summary>@MappedSuperClass</summary>
<br>

<br>
</details>



<details>
<summary></summary>
<br>

<br>
</details>


<br>
<br>

## 8. 기타 개선한 부분

<br>
<br>

## 9. 회고/ 느낀점
