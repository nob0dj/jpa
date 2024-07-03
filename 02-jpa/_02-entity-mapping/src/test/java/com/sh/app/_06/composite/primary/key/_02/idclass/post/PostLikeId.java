package com.sh.app._06.composite.primary.key._02.idclass.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeId implements Serializable {
    private Long postId;
    private Long userId;
}
