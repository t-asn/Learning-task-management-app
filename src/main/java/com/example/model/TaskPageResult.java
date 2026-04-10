package com.example.model;

import java.util.List;

/**
 * ページングの結果（データリストと総件数）を保持するレコード。
 */
public record TaskPageResult(List<Task> tasks, long totalCount) {

}