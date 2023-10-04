package com.platform.common.dto.novel;

import com.platform.common.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NovelCreateDTO  {
    private String title;
    private NovelStatus status;
    private String coverImage;
    private Long totalWord;
    private Long totalChapter;
    private Long totalLike;
    private Long totalView;
    private Long totalVote;
    private String overview;
    private Boolean crawling;
    private String[] tags;
    private Long authorId;
}
