package kr.kw.matcher.module.chat.chat.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.kw.matcher.module.chat.chat.domain.Chat;
import kr.kw.matcher.module.chat.chat.domain.ChatType;
import kr.kw.matcher.module.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long id;

    private Long userId;

    private UserBriefDto user;

    private Long roomId;

    private Long seqId;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDt;

    private ChatType chatType;

    private String text;

    private String imageUrl;

    private String imageThumbUrl;

    private String locationX;

    private String locationY;

    public static ChatDto ofTextAndUser(Chat chat, User user) {
        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setUserId(chat.getUserId());
        chatDto.setRoomId(chat.getRoomId());
        chatDto.setSeqId(chat.getSeqId());
        chatDto.setCreatedDt(chat.getCreatedDt());
        chatDto.setChatType(chat.getChatType());
        chatDto.setText(chat.getText());
        chatDto.setImageUrl(chat.getImageUrl());
        chatDto.setImageThumbUrl(chat.getImageThumbUrl());
        chatDto.setLocationX(chat.getLocationX());
        chatDto.setLocationY(chat.getLocationY());

        UserBriefDto userDto = new UserBriefDto();
        userDto.setId(user.getId());
        userDto.setNickname(user.getNickname());
        userDto.setUniversity(user.getUniversity());
        userDto.setProfileThumbUrl(user.getProfileThumbUrl());
        userDto.setUnivVerified(user.getUnivVerified());
        chatDto.setUser(userDto);

        return chatDto;
    }
    public static ChatDto ofText(Chat chat) {
        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setUserId(chat.getUserId());
        chatDto.setRoomId(chat.getRoomId());
        chatDto.setSeqId(chat.getSeqId());
        chatDto.setCreatedDt(chat.getCreatedDt());
        chatDto.setChatType(chat.getChatType());
        chatDto.setText(chat.getText());
        chatDto.setImageUrl(chat.getImageUrl());
        chatDto.setImageThumbUrl(chat.getImageThumbUrl());
        chatDto.setLocationX(chat.getLocationX());
        chatDto.setLocationY(chat.getLocationY());

        return chatDto;
    }
}