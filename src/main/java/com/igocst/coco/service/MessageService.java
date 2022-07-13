package com.igocst.coco.service;

import com.igocst.coco.common.status.StatusCode;
import com.igocst.coco.common.status.StatusMessage;
import com.igocst.coco.domain.Member;
import com.igocst.coco.domain.Message;
import com.igocst.coco.dto.message.*;
import com.igocst.coco.repository.MemberRepository;
import com.igocst.coco.repository.MessageRepository;
import com.igocst.coco.security.MemberDetails;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    // 쪽지 보내기
    @Transactional
    public ResponseEntity<MessageCreateResponseDto> join(MessageCreateRequestDto messageCreateRequestDto, MemberDetails memberDetails) {

        Member sendMember = memberRepository.findByEmail(memberDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("보내는 사람이 존재하지 않습니다."));


        String receiver = messageCreateRequestDto.getReceiver();
        Member received = memberRepository.findByNickname(receiver)
                .orElseThrow(() -> new IllegalArgumentException("받는 사람이 존재하지 않습니다."));

        // message 보내는 코드
        Message message = Message.builder()
                .receiver(received)
                .title(messageCreateRequestDto.getTitle())
                .content(messageCreateRequestDto.getContent())
                .createDate(messageCreateRequestDto.getCreateDate())
                .build();

        sendMember.sendMessage(message);
        messageRepository.save(message);

        return new ResponseEntity<>(
                MessageCreateResponseDto.builder()
                        .status(StatusMessage.SUCCESS)
                        .build(),
                HttpStatus.valueOf(StatusCode.SUCCESS)
        );

    }


    // 쪽지 상세 읽기
    @Transactional
    public ResponseEntity<MessageReadResponseDto> getMessage(Long messageId, MemberDetails memberDetails) {

        Member member = memberRepository.findByEmail(memberDetails.getMember().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("쪽지에 대한 권한이 없습니다."));

        Message message = member.findMessage(messageId);
        if (message == null) {
//            throw new RuntimeException("쪽지에 대한 권한이 없습니다.");
            return new ResponseEntity<>(
                    MessageReadResponseDto.builder()
                            .status(StatusMessage.BAD_REQUEST)
                            .build(),
                    HttpStatus.valueOf(StatusCode.BAD_REQUEST)
            );
        }

        message.setReadState(true);

        return new ResponseEntity<>(
                MessageReadResponseDto.builder()
                        .sender(message.getSender().getEmail())
                        .title(message.getTitle())
                        .content(message.getContent())
                        .readState(message.isReadState())
                        .status(StatusMessage.SUCCESS)
                        .build(),
                HttpStatus.valueOf(StatusCode.SUCCESS)
        );

    }


    // 쪽지 리스트 읽기
    @Transactional
    public ResponseEntity<List<MessageListReadResponseDto>> getMessageList(@AuthenticationPrincipal MemberDetails memberDetails) {

        String readMember = memberDetails.getUsername();

        Member member = memberRepository.findByEmail(readMember)
                .orElseThrow(() -> new IllegalArgumentException("쪽지에 대한 권한이 없습니다."));

        List<Message> messages = member.getReadMessage();

        List<MessageListReadResponseDto> messageList = new ArrayList<>();
        for (Message m : messages) {
            messageList.add(MessageListReadResponseDto.builder()
                    .id(m.getId())
                    .title(m.getTitle())
                    .sender(m.getSender().getNickname())
                    .readState(m.isReadState())
                    .createDate(m.getCreateDate())
                    .status(StatusMessage.SUCCESS)
                    .build());
        }
        return new ResponseEntity<>(messageList, HttpStatus.valueOf(StatusCode.SUCCESS));
    }


    // 쪽지 삭제
    @Transactional
    public ResponseEntity<MessageDeleteResponseDto> deleteMessage(Long messageId, @AuthenticationPrincipal MemberDetails memberDetails) {

        Member member = memberRepository.findByEmail(memberDetails.getMember().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("쪽지에 대한 권한이 없습니다."));

        boolean isValid = member.deleteMessage(messageId);

        if (!isValid) {
//            throw new RuntimeException("쪽지를 삭제할 수 있는 권한이 없습니다.");
            return new ResponseEntity<>(
                    MessageDeleteResponseDto.builder()
                            .status(StatusMessage.BAD_REQUEST)
                            .build(),
                    HttpStatus.valueOf(StatusCode.BAD_REQUEST)
            );
        }

        messageRepository.deleteById(messageId);

        return new ResponseEntity<>(
                MessageDeleteResponseDto.builder()
                        .status(StatusMessage.SUCCESS)
                        .build(),
                HttpStatus.valueOf(StatusCode.SUCCESS)
        );

    }
}