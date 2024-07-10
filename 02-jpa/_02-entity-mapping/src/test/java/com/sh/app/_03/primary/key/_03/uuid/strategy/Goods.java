package com.sh.app._03.primary.key._03.uuid.strategy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tbl_goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
}
