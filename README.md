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


## 5. 개발 담당 기능 소개
- JPA 양방향관계 매핑을 사용한 쪽지 CRD 
    - 쪽지 보내기, 쪽지 답장 보내기, 보낸&받은 쪽지리스트, 쪽지 상세보기, 쪽지 삭제
- JPA 양방향관계 매핑을 사용한 북마크 CRD 
    - 게시글 북마크에 저장, 북마크 리스트 획득, 북마크 삭제
- Spring Data JPA 활용한 게시글 기능
    - 글 작성 유저에게 쪽지 보내기 & 프로필 보기, 게시글 조회수, 모집현황 필터
- JPA 양방향관계 매핑을 사용한 회원 탈퇴 기능

<br>
<br>

## 6. 기억에 남는 기능
<details>
<summary> 쪽지 상세읽기 </summary>
https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/MessageService.java#L73-L105

### 기억에 남는 이유
- 초기에 쪽지를 보낼 때 발신 & 수신 유저를 이메일로 구별했는데, 유저의 입장에서 닉네임을 이용하는 것이 접근성과 사용성이 편하다고 판단하여 닉네임으로 쪽지발신과 수신을 하게끔 변경했던 기능입니다. 이는 프로젝트에서 처음으로 사용자 친화적으로 생각하여 개선시켰던 경험이기 때문에 기억에 남았습니다.<br>
- 기존에 받은 쪽지들만 확인할 수 있었지만 고객 피드백에서 '보낸 쪽지도 확인할 수 있으면 좋겠다'라는 의견이 있었고, 좋은 피드백이였기에 수용하여 기능을 구현을 했습니다.
처음에는 Service에서 보낸 쪽지들을 불러오는 메소드를 한개 더 만드려고 했었는데, 비효율적이라 생각했고 조금 더 나은 방법을 고민하던 중에 Member Entity에서 기존에 선언해준 받은 메시지만 찾는 [findMessage](https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/domain/Member.java#L135-L151) 메소드에 보낸 쪽지도 한번에 찾을 수 있도록 해서 효율적으로 코드 작성할 수 있었습니다. <br>
- `읽음`, `읽지않음` 기능을 추가했습니다. 쪽지를 받은 유저(수신자)가 쪽지를 읽으면 본인 & 쪽지를 보낸 유저(발신자) 모두 쪽지를 읽었는지 확인할 수 있도록 사용자의 편의성을 생각하여 개발했습니다. 보낸사람이 본인이 보낸 쪽지를 읽어도 쪽지의 상태는 변경되지 않습니다.<br>
- 같은 status code여도 status message를 [다르게 부여](https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/MessageService.java#L38-L55)할 수 있고, 프론트엔드에도 이 부분을 연결해서 유저에게 백엔드의 status message에 따라서 alert를 다르게 보낼 수 있다는 것도 알게 됐습니다. <br>
- 처음으로 만들었던 기능인만큼 프로젝트의 처음부터 끝까지 계속해서 빌드업하고 수정 작업이 굉장히 많았기 때문에 가장 기억에 남습니다.<br>

<br><br>
</details>
<details>
<summary>북마크</summary>
https://github.com/serijang/CoCoBackend/blob/23dbd833cc967b3ae52e753ef1f0eb91f23d67a1/src/main/java/com/igocst/coco/service/BookmarkService.java#L32-L69

### 기억에 남는 이유
- 처음으로 온전히 혼자서 만들어 본 북마크 기능입니다. 간단할 것 같았지만 구글 검색시에도 정보를 잘 찾을 수 없었고 Member & Post 두 개의 Entity와 연관 관계가 맺어져 있어서 어려웠기 때문에 기억에 남습니다. 
- 이미 저장한 북마크는 저장이 불가하도록 [예외처리](https://velog.io/@serringg/%EB%B6%81%EB%A7%88%ED%81%AC)를 하고, 한 개의 게시글에는 한 명의 유저만 저장되는 오류가 있어서 문제를 해결하면서 공부가 많이 됐습니다.
- 프로젝트 기간이 끝난 후에도 프로젝트 완성도를 높이기 위해 북마크 추가 기능을 구현하고 리팩토링하면서 완성했던 경험이 있어서 더 기억에 남습니다.
    - 추가적으로 구현한 기능이기 때문에 최대한 기존 코드의 변경없이 효율적으로 작성하기위해 노력했습니다.
    - 로그인한 유저의 북마크 정보를 획득하는 기존 메소드를 홈 화면의 게시글 리스트를 불러오는 ajax의 마지막에 사용하여, 홈 화면 게시글 리스트에서 유저가 북마크한 `postId`가 게시글의 `postId`와 같다면 북마크 아이콘 색이 변경되도록 [구현](https://github.com/BreedingMe/CoCoFrontend/commit/1620f5de9afd5b2d1dbdde93c80279b544bca84e)하였습니다.
<br>
</details>

<details>
<summary> 게시글 작성 유저의 정보 불러오기 </summary>
https://github.com/serijang/CoCoBackend/blob/6cf6235b0d71901a02b25ce9bcd3c50704fb95d3/src/main/java/com/igocst/coco/service/PostService.java#L71-L130

### 기억에 남는 이유
- 글 작성자에게 쪽지를 보낼 때와 프로필을 볼 때 필요한 정보들을 불러올 때 따로 메소드를 만들지 않고, 기존에 구현돼있던 게시글 상세페이지를 불러오는 DTO와 Service에서 [최소한의 코드만을 추가](https://github.com/BreedingMe/CoCoBackend/commit/45ceb4ef79cb4772878dcd9f15024c6c6a8ec280)하여 구현할 수 있도록 목표했습니다.

- 프론트에서도 불필요한 ajax를 제거하고 코드 리팩토링을 통해 97줄 -> 27줄로 [축소](https://github.com/BreedingMe/CoCoFrontend/pull/163/files)에 성공해서 뿌듯했습니다.<br><br>

</details>

<details>
<summary> 모집현황 필터 (Front-End)</summary>

```javascript
window.initializeHome = () => {
    let check = localStorage.getItem('check');
    $('#recruitmentStateCheckbox').prop('checked', JSON.parse(check));
    if ($('input:checkbox[id="recruitmentStateCheckbox"]').is(':checked')) {
        getPosts();
    }
    else {
        getRecrutingPosts();
    }
};

window.recruitmentStateCheckbox = () => {
    localStorage.setItem('check', $('#recruitmentStateCheckbox').is(':checked'));

    if ($('input:checkbox[id="recruitmentStateCheckbox"]').is(':checked')) {
        getPosts();
    }
    else {
        getRecrutingPosts();
    }
};
```

### 기억에 남는 이유

- 기존에는 홈화면에서 모든 게시글을 불러왔는데, 고객피드백을 반영하여 기본적으로는 `모집중`인 게시글만 보이게하고 '모집완료된 프로젝트 포함' 필터에 체크하면 `모집중 & 모집완료` 게시물을 모두 볼 수 있도록 기능을 구현하였습니다.
    - `localStorage`를 이용하여 게시글을 들어갔다가 뒤로가기해도 필터링 상태가 유지되도록 만드는 부분에서 많은 어려움을 겪었고, 해결했을 때 뿌듯함을 느꼈기 때문입니다.<br>
</details>
<br>
<br>

## 7. 트러블 슈팅

###  7-1 북마크 객체

#### 문제 원인<br>
- 모든 유저들은 본인이 북마크한 게시글 목록들을 각각 가질 수 있어야 하는데, 한 개의 게시글을 한 명의 유저만 북마크에 저장할 수 있는 오류가 발생했습니다.

> 해결 방법<br>

북마크를 저장할 때 `new` 연산자를 사용해서 객체를 새로 생성해줌으로써 문제를 해결했습니다.

<details>
<summary> 기존 방식 </summary>
<br>
| BookmarkService.java

```java
    // 북마크에 저장하기
    @Transactional
    public ResponseEntity<BookmarkSaveResponseDto> saveBookmark(Long postId, MemberDetails memberDetails) {
        Optional<Member> memberOptional = memberRepository.findById(memberDetails.getMember().getId());
        Member member = memberOptional.get();

        Optional<Post> postOptional  = postRepository.findById(postId);
        if(postOptional.isEmpty()) {
            log.error("nickname={}, postId={}, error={}", member.getNickname(), postId, "해당 게시글을 찾을 수 없음");
            return new ResponseEntity<>(
                    BookmarkSaveResponseDto.builder().status(StatusMessage.BAD_REQUEST).build(),
                    HttpStatus.valueOf(StatusCode.BAD_REQUEST));
        }
        Post post = postOptional.get();

        List<Bookmark> bookmarks = bookmarkRepository.findAllByPostId(postId);
        for(Bookmark b : bookmarks) {

            if (b.getMember().getId() == memberDetails.getMember().getId()) {
                log.error("nickname={}, postId={}, error={}", member.getNickname(), postId, "이미 북마크에 저장한 게시글");
                return new ResponseEntity<>(
                        BookmarkSaveResponseDto.builder().status(StatusMessage.DUPLICATED_BOOKMARK).build(),
                        HttpStatus.valueOf(StatusCode.BAD_REQUEST)
                );
            }
        }

        Bookmark bookmark = Bookmark.builder()  // 변경 한 부분
                .id(post.getId())
                .build();

        member.addBookmark(bookmark);
        post.addBookmark(bookmark);
        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>(
                BookmarkSaveResponseDto.builder().status(StatusMessage.SUCCESS).build(),
                HttpStatus.valueOf(StatusCode.SUCCESS)
        );
    }
```

<br>
</details>

<details>
<summary> 해결 방법 </summary>
<br>
| BookmarkService.java

```java
    // 북마크에 저장하기
    @Transactional
    public ResponseEntity<BookmarkSaveResponseDto> saveBookmark(Long postId, MemberDetails memberDetails) {
        Optional<Member> memberOptional = memberRepository.findById(memberDetails.getMember().getId());
        Member member = memberOptional.get();

        Optional<Post> postOptional  = postRepository.findById(postId);
        if(postOptional.isEmpty()) {
            log.error("nickname={}, postId={}, error={}", member.getNickname(), postId, "해당 게시글을 찾을 수 없음");
            return new ResponseEntity<>(
                    BookmarkSaveResponseDto.builder().status(StatusMessage.BAD_REQUEST).build(),
                    HttpStatus.valueOf(StatusCode.BAD_REQUEST));
        }
        Post post = postOptional.get();

        List<Bookmark> bookmarks = bookmarkRepository.findAllByPostId(postId);
        for(Bookmark b : bookmarks) {

            if (b.getMember().getId() == memberDetails.getMember().getId()) {
                log.error("nickname={}, postId={}, error={}", member.getNickname(), postId, "이미 북마크에 저장한 게시글");
                return new ResponseEntity<>(
                        BookmarkSaveResponseDto.builder().status(StatusMessage.DUPLICATED_BOOKMARK).build(),
                        HttpStatus.valueOf(StatusCode.BAD_REQUEST)
                );
            }
        }

        Bookmark bookmark = new Bookmark();  // 변경된 부분
        member.addBookmark(bookmark);
        post.addBookmark(bookmark);
        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>(
                BookmarkSaveResponseDto.builder().status(StatusMessage.SUCCESS).build(),
                HttpStatus.valueOf(StatusCode.SUCCESS)
        );
    }

```

<br>
</details>
<br><br>

### 7-2 쪽지 상세읽기 구분

#### 문제 원인<br>

- 쪽지 상세읽기(Get)를 하면 읽음 상태가 `읽지않음(false)` → `읽음(true)` 으로 [변경](https://github.com/BreedingMe/CoCoBackend/blob/77dcb6b55af6b6b02587e03919dfde0bc77a3f49/src/main/java/com/igocst/coco/service/MessageService.java#L91)되는데, 보낸 쪽지함에서 본인이 보낸 쪽지를 읽을 때에도 상대방 쪽지의 읽음 상태가 변경되는 문제가 있었습니다.
> 해결 방법
- 쪽지 상세읽기를 할 때, 쪽지의 `sender`와 `receiver`와 동일하다면 `읽지않음(false)` → `읽음(true)`가 변하지 않도록 분기처리
- `sender == receiver` 일 때 읽음상태가 변하지 않는데, 받은 쪽지함에서 본인이 보낸 쪽지를 확인해도 읽음상태가 변하지않는 이상현상을 예방하기 위해 아예 본인에게 쪽지를 보낼 수 없도록 제한
- 프론트에서도 보낸 쪽지는 답장보내기 버튼이 '없는' 모달 & 받은 쪽지를 확인할 때는 답장보내기 버튼이 '있는' 모달이 뜨도록 구분하여 보여질 수 있도록 분기처리<br>

[🔗 MessageService.java 변경 사항](https://github.com/BreedingMe/CoCoBackend/commit/56ab117e985c12b889578f9ea993dfa792e3497c)   /   [🔎 더 자세한 쪽지 상세읽기 기능의 변화](https://velog.io/@serringg/%EC%AA%BD%EC%A7%80-%EC%83%81%EC%84%B8%EC%9D%BD%EA%B8%B0-%EA%B8%B0%EB%8A%A5%EC%9D%98-%EB%B3%80%EC%B2%9C%EC%82%AC)<br><br><br>


### 7-3 @MappedSuperClass<br>

#### 문제 원인<br>

- 초기 쪽지 Entity를 설계할 때, 쪽지를 보낼 때 기록되는 생성시간인 `createDate` column을 `@MappedSuperClass` 로 공통된 정보를 매핑해서 사용하는 class를 따로 만들어서 상속받아 사용했습니다.
- 이 경우에 쪽지를 보낼 때 생성시간이 DB에 저장은 되지만, 데이터를 조회 & 이용할 수 없기 때문에 클라이언트로 데이터를 보낼 수 없는 문제를 발견했습니다.

> 해결 방법 1

- Message Entity에 LocalDateTime class를 이용해서 `createDate` column 추가<br>
- MessageCreateRequestDto에서 쪽지 보내는 시간 기준으로 LocalDateTime을 이용해서 createDate 생성

> 해결 방법 2

- `@MappedSuperClass` class에 @Getter 를 추가하여 데이터를 이용할 수 있도록 변경
<br>

<details>
<summary> 기존 방식 </summary>
<br>
| Timestamped.java

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    private LocalDateTime createDate;
}
```

<br>
</details>

<details>
<summary> 해결 방법 </summary>
<br>

> 방법 1

| Message.java

```java
public class Message {
    @Column(nullable = false)
    private LocalDateTime createDate;
}
```
<br>
| MessageCreateRequestDto.java

```java
public class MessageCreateRequestDto {
    private LocalDateTime createDate = LocalDateTime.now();
}
```
<br>

> 방법 2

| Timestamped.java

```java
@Getter // Getter 추가
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    private LocalDateTime createDate;
}
```
</details>

<br><br>


## 8. 그외 트러블 슈팅

📍 [Optional 사용](https://github.com/BreedingMe/CoCoBackend/wiki/Optional-%EC%82%AC%EC%9A%A9)<br>
📍 [ResponseEntity 예외 처리](https://github.com/BreedingMe/CoCoBackend/wiki/%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC)<br>
📍 [Setter 사용하지 않기](https://github.com/BreedingMe/CoCoBackend/wiki/@Setter-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EC%95%8A%EA%B8%B0)
<br>
<br>

## 9. 회고/ 느낀점
[프로젝트 회고](https://velog.io/@serringg/220810-%EC%B5%9C%EC%A2%85%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0-CoCo-Spring)
