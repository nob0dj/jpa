package com.sh.entity.mapping._03.element.collection._01.set._02.auth.embeddable.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Role2 {
    @Id
    private String id;
    private String name;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER) // fetch 기본값 FetchType.LAZY
    @CollectionTable(
        name = "role_permission2",
        joinColumns = @JoinColumn(name = "role_id")
    )
    private Set<GrantedPermission> permissions = new HashSet<>();

    public void revokeAll() {
        this.permissions.clear(); // SELECT -> DELETE
        // this.permissions = new HashSet<>(); // DELETE
    }

}
