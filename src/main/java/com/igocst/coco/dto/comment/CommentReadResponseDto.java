package com.igocst.coco.dto.comment;

import lombok.*;

@Builder
@Setter
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReadResponseDto {
    private Long id;
    private String comments;
    //List가 아니라 String으로 해줘야함 왜냐하면, Comment entity에  getContent할 content가 String 타입이니까.
    private String nickname;
    private String status;
}