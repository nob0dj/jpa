package com.sh.app._06.composite.primary.key._02.idclass.post;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity(name = "PostLike0602")
@Table(name = "tbl_post_like0602")
@Data
@IdClass(PostLikeId.class)
public class PostLike implements Serializable {
    @Id
    @Column(name = "post_id")
    private Long postId;
    @Id
    @Column(name = "user_id")
    private Long userId;
}
