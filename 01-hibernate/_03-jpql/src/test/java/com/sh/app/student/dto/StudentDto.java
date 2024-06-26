package com.sh.app.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 프로젝션용 Dto클래스
 * - entity로 등록하지 않는다.
 */
@Data
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
}
