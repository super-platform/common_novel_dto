package com.platform.common.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorCreateDTO {
    private String pseudonym;

    private String fullName;

    private String avatar;

    private String yearOfBirth;

    private Double rating;

    private String nationality;

    private String ethnic;

    private String birthPlace;

    private String occupation;
    private String overview;
    private String[] tags;
}
