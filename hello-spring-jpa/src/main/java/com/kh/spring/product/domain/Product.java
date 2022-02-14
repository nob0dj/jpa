package com.kh.spring.product.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.kh.spring.category.domain.Category;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "product_category",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new LinkedHashSet<>();

	/**
	 * @ManyToMany 편의메소드
	 * @param category
	 */
	public void addCategory(Category category) {
		this.categories.add(category);
		if(category != null && !category.getProducts().contains(this))
			category.getProducts().add(this);
	}
	
}
