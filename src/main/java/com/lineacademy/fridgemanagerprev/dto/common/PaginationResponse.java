package com.lineacademy.fridgemanagerprev.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class PaginationResponse<T> {
    private int page;
    private int size;
    private Long total;
    private List<T> list;

    public static <T> PaginationResponse<T> of(int page, int size, Long total, List<T> list) {
        return PaginationResponse.<T>builder()
                .page(page)
                .size(size)
                .total(total)
                .list(list)
                .build();

    }
}

// 우리가 Map.of("message","메세지내용',"data","어쩌구")메서드를 사용해서
// 새로운 객체를 만들어냈었던 것 처럼
// PaginationResponse.of()를 통해 PaginationResponse 객체를 만들어내기 위함
