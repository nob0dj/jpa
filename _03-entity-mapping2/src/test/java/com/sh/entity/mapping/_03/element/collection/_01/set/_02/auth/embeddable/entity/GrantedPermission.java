package com.sh.entity.mapping._03.element.collection._01.set._02.auth.embeddable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class GrantedPermission {
    @Column(name = "perm")
    private String permission;
    private String grantor;
}
