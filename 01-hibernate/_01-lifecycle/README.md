# hibernate
> hibernate api자체와 jpa의 경계 학습용 프로젝트입니다.

## 영속성관리객체
###### SessionFactory | Session
- hibernate 전용 영속성 관리객체
- 설정파일 `src/main/resources/hibernate.cfg.xml` 

###### EntityMangerFactory | EntityManager
- jpa 표준 영속성 관리객체
- 설정파일 `src/main/resourcesMETA-INF/persistence.xml` 

### user 영속성 테스트 
@com.sh.app.user.UserPersistencTest

### mvc service 통합테스트
UserServiceProxy를 사용해서 트랜잭션을 업무로직 밖에서 관리

@com.sh.app.user.service.UserServiceTest

### mvc repository mock 테스트
@com.sh.app.user.repository.UserRepositoryMockTest
@com.sh.app.user.repository.UserRepositoryBDDMockitoTest

### mvc service mock 테스트
@com.sh.app.user.service.UserServiceMockTest

## jpql
@com.sh.app.student.StudentJpqlTest


## 연관관계

### N:1
@com.sh.app.member
- Member (N) `@ManyToOne`
- Team (1) `@OneToMany`

### N:M


### 1:1





