# Query DSL

## ⚙️환경설정
@build.gradle
```groovy
dependencies {
    // queryDSL
    implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
    annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
    
    // spring-data-jpa, mysql-driver 모두 추가
}
```

entity클래스 추가후 build task 실행
![](https://d.pr/i/sfE2zi+)

QType 클래스 생성확인
![](https://d.pr/i/VN3Jmt+)

Config 클래스 작성 
```java
@Configuration
public class QueryDSLConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
```
Repository클래스 작성
```java
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
```

## JpaRepository와 혼용하기
Query메소드와 QueryDSL 구현메소드를 동시에 사용할 수 있다.
![](https://d.pr/i/hUPcup+)