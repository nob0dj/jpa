package com.sh.entity.association._01.one2one._02.identifying.vote.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "User2")
@Table(name = "user2")
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