# 03-entity-mapping2

## 02-embeddable
### 1. value를 같은 테이블에 저장하는 경우
#### @com.sh.entity.mapping._02.embeddable._01.single.table
* hotel @Embedded 1개
* address @Embedded n개 (@AttributeOverrides)

### 2. value를 다른 테이블에 저장하는 경우
#### @com.sh.entity.mapping._02.embeddable._02.other.table 
다음 2가지 설정이 필요하다.
1. entity클래스에 @SecondaryTable로 등록
2. 두번째 테이블 컬럼에 해당하는 필드에도 테이블명을 명시해야 한다.

작성
* 방법1(writer_intro): @Embeddable클래스의 필드 @Column에 두번째테이블명 등록
* 방법2(writer_address): @Entity클래스 필드 @Embbeded 필드에 @AttributeOverrides하위에 두번째 테이블명 등록

## 03-element-collection
### 1. Set

### 2. List

### 3. Map