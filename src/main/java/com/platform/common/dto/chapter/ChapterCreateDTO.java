package com.platform.common.dto.chapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChapterCreateDTO {
    private String title;
    private Long episode;
    private Long totalWord;
    private Long totalView;
    private String schedule;
    private String content;
    private Long novelId;
}
