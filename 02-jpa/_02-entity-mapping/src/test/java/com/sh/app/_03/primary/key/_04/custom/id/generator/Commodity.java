package com.sh.app._03.primary.key._04.custom.id.generator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "tbl_commodity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commodity {
    @Id
    @GeneratedValue(generator = "prefix-generator")
    @GenericGenerator(
            name = "prefix-generator",
            parameters = @Parameter(name = "prefix", value = "sh"),
            strategy = "com.sh.app._03.primary.key._04.custom.id.generator.PrefixGenerator")
    private String id;
    private String name;
}
