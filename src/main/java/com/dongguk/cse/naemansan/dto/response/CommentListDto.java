package com.dongguk.cse.naemansan.dto.response;

import com.dongguk.cse.naemansan.dto.TagDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListDto {
    private Long id;
    private Long course_id;
    private String course_title;
    private String content;
    private List<TagDto> tags;

    @Builder
    public CommentListDto(Long id, Long course_id, String course_title, String content, List<TagDto> tags) {
        this.id = id;
        this.course_id = course_id;
        this.course_title = course_title;
        this.content = content;
        this.tags = tags;
    }
}
