package org.bazar.chat.app.api.message.dto;

import java.util.List;

public record GetMessagePageDto(
        List<GetMessageDto> content,
        Integer page,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
) {
}
