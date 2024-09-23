package com.enigmacamp.enigmacoop.model.response;

import com.enigmacamp.enigmacoop.model.response.PagingResponse;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private String status;
    private String message; // berhasil create data nasabah, berhasil delte data nasabah. etc.
    private T data;
    private PagingResponse paging;
}