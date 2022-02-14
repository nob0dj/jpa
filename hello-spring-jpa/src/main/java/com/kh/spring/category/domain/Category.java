package com.kh.spring.category.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.kh.spring.product.domain.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "products")
public class Category {

	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private CategoryName categoryName;
	
	@ManyToMany(mappedBy = "categories")
	private List<Product> products = new ArrayList<>();
}
