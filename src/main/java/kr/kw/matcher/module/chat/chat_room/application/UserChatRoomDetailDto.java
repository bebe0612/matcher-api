package kr.kw.matcher.module.chat.chat_room.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import kr.kw.matcher.module.file.domain_image.domain.DomainType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserChatRoomDetailDto {
    private Long id;
    private String name;
    private String imageUrl;
    private Long memberCount;
    private ChatRoomStatus status;
    private DomainType domainType;
    private Long domainId;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDt;
    private Boolean allowChatPushYn;

    public static QBean<UserChatRoomDetailDto> getBean(QChatRoom chatRoom,
                                                       QChatRoomDomain chatRoomDomain,
                                                       QChatRoomMember chatRoomMember) {
        return Projections.bean(UserChatRoomDetailDto.class,
                chatRoom.id,
                chatRoom.name,
                chatRoom.imageUrl,
                chatRoom.memberCount,
                chatRoom.status,
                chatRoomDomain.domainType,
                chatRoomDomain.domainId,
                chatRoom.createdDt,
                chatRoomMember.allowChatPushYn);
    }
}