package com.platform.common.dto.chapter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ChapterDTO  {
    private Long id;
    private String title;
    private Long episode;
    private Long totalWord;
    private Long totalView;
    private String schedule;
    private String content;
}
