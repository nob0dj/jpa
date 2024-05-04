package com.sh.entity.mapping._03.element.collection._01.set._01.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Role {
    @Id
    private String id;
    private String name;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER) // fetch 기본값 FetchType.LAZY
    @CollectionTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "perm") // role_permission테이블 perm 컬럼
    private Set<String> permissions = new HashSet<>();

    public void revokeAll() {
        this.permissions.clear(); // SELECT -> DELETE
        // this.permissions = new HashSet<>(); // DELETE
    }

}
