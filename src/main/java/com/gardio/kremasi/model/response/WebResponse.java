package com.gardio.kremasi.model.response;

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