package org.bazar.chat.app.api.message.dto;

import java.util.List;

/**
 * DTO для получения информации по сообщениям с пагинацией
 *
 * @param content Информация по сообщениям
 * @param page Номер страницы
 * @param pageSize Количество записей в странице
 * @param totalElements Общее количество элементов
 * @param totalPages Общее количество страниц
 */
public record GetMessagePageDto(
        List<GetMessageDto> content,
        Integer page,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
) {
}
