package com.sh.app._01.qtype;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sh.app._01.qtype.QMenu.menu;

@Repository
@RequiredArgsConstructor
public class MenuRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Menu> findByCategoryCode(Long categoryCode) {
        return jpaQueryFactory.selectFrom(menu)
                .where(menu.categoryCode.eq(categoryCode).and(menu.orderableStatus.eq("Y")))
                .orderBy(menu.menuCode.asc())
                .fetch();
    }
}
