package com.dongguk.cse.naemansan.dto.request;

import com.dongguk.cse.naemansan.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTagRequestDto {
    List<TagDto> tags;
}