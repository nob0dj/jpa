package com.sh.entity.mapping._04.inheritance._02.joined._01.product.repository;

import com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity.Book;
import com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity.Clothing;
import com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    /*
        create table product (
            price integer not null,
            id bigint not null auto_increment,
            dtype varchar(31) not null,
            name varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table book (
            id bigint not null,
            author varchar(255),
            isbn varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table clothing (
            id bigint not null,
            material varchar(255),
            size varchar(255),
            primary key (id)
        ) engine=InnoDB
     */
    @DisplayName("Book 등록")
    @Test
    public void test() throws Exception {
        // given
        Book book = new Book(null, "I Love JPA", 30000, "최범균","1234567890");
        // when
        book = productRepository.save(book);
        System.out.println(book);
        // then
        assertThat(book.getId()).isNotNull().isNotZero();
    }
    @DisplayName("Clothing 등록")
    @Test
    public void test2() throws Exception {
        // given
        Clothing clothing = new Clothing(null, "무지T", 30000, "XXL","순면100%");
        // when
        clothing = productRepository.save(clothing);
        System.out.println(clothing);
        // then
        assertThat(clothing.getId()).isNotNull().isNotZero();
    }

    @DisplayName("Book, Clothing 조회")
    @Test
    void test3() {
        // given
        Book book = new Book(null, "I Love JPA", 30000, "최범균","1234567890");
        Clothing clothing = new Clothing(null, "무지T", 30000, "XXL","순면100%");
        productRepository.save(clothing);
        productRepository.save(book);
        // when
        List<Product> products = productRepository.findAll();
        System.out.println(products);
        // then
        assertThat(products)
            .allSatisfy(product -> {
                assertThat(product).satisfiesAnyOf(
                    p -> assertThat(p).isInstanceOf(Book.class),
                    p -> assertThat(p).isInstanceOf(Clothing.class)
                );
            });

    }
    /*
    test3 실행된 sql (select도 실제 질의한다.)

    Hibernate:
        insert
        into
            product
            (name, price, dtype)
        values
            (?, ?, 'clothing')
    Hibernate:
        insert
        into
            clothing
            (material, size, id)
        values
            (?, ?, ?)
    Hibernate:
        insert
        into
            product
            (name, price, dtype)
        values
            (?, ?, 'book')
    Hibernate:
        insert
        into
            book
            (author, isbn, id)
        values
            (?, ?, ?)
    Hibernate:
        select
            p1_0.id,
            p1_0.dtype,
            p1_0.name,
            p1_0.price,
            p1_1.author,
            p1_1.isbn,
            p1_2.material,
            p1_2.size
        from
            product p1_0
        left join
            book p1_1
                on p1_0.id=p1_1.id
        left join
            clothing p1_2
                on p1_0.id=p1_2.id
     */
}