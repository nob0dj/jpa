package com.sh.app._06.composite.primary.key._02.idclass;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorClazzId {
    private Long professorId;
    private Long clazzId;
}
