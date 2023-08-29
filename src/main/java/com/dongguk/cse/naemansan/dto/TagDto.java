package com.dongguk.cse.naemansan.dto;

import com.dongguk.cse.naemansan.domain.type.ECourseTag;
import com.dongguk.cse.naemansan.domain.type.ETagStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private ECourseTag name;
    private ETagStatus status;
}
