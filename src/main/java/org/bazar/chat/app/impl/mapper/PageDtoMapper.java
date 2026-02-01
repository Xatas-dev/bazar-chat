package org.bazar.chat.app.impl.mapper;

import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageDtoMapper {
    public GetMessagePageDto toGetMessagePageDto(Page<GetMessageDto> page) {
        return new GetMessagePageDto(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
