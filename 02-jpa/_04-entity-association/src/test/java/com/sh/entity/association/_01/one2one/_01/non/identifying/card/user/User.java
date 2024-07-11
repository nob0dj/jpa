package com.sh.entity.association._01.one2one._01.non.identifying.card.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class User {
    @Id
    private String email;
    private String name;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void changeName(String newName) {
        this.name = newName;
    }
}