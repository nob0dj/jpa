package com.sh.app._06.composite.primary.key._01.embeddedid.post;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeId implements Serializable {
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "user_id")
    private Long userId;
}
