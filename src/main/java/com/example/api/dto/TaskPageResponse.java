package com.example.api.dto;

import java.util.List;

/**
 * ページング付きタスク一覧。
 */
public record TaskPageResponse(
    List<TaskResponse> tasks,
    long totalCount,
    int page,
    int size
) {

}
