package com.sh.app._06.composite.primary.key._01.embeddedid.post;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "PostLike")
@Table(name = "post_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {
    @EmbeddedId
    private PostLikeId id;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
