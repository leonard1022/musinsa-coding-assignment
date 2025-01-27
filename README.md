# 무신사 코딩 과제

## 프로젝트 개요
이 프로젝트는 무신사 코딩 과제로, 상품과 브랜드를 관리하는 시스템입니다. Spring Boot와 Kotlin을 사용하여 개발되었습니다.

## 기술 스택
- Kotlin
- Spring Boot
- JPA (Hibernate)
- Gradle
- H2 Database (테스트용)

## 프로젝트 구조
- `entity`: 데이터베이스 엔티티 클래스
- `repository`: 데이터베이스 접근을 위한 리포지토리 인터페이스
- `service`: 비즈니스 로직을 처리하는 서비스 클래스
- `vo`: 값 객체 (Value Object) 클래스
- `controller`: HTTP 요청을 처리하는 컨트롤러 클래스
- `dto`: 데이터 전송 객체
- `exception`: 커스텀 예외 클래스

## 주요 기능
1. **상품 생성**: 새로운 상품을 생성합니다.
2. **상품 조회**: 특정 상품을 조회합니다.
3. **상품 수정**: 기존 상품의 정보를 수정합니다.
4. **상품 삭제**: 특정 상품을 삭제합니다.
5. **카테고리별 최저가 상품 조회**: 각 카테고리별 최저가 상품을 조회합니다.
6. **단일 브랜드 최저가 상품 조회**: 단일 ���랜드로 모든 카���고리 상품을 최저가로 구매할 수 있는 정보를 조회합니다.
7. **카테고리별 최저가 및 최고가 상품 조회**: 각 카테고리별 최저가 및 최고가 상품을 조회합니다.

## 설정 및 실행 방법
1. **프로젝트 클론**
    ```bash
    git clone https://github.com/jooini/musinsa-coding-assignment.git
    cd musinsa-coding-assignment
    ```

2. **Gradle 빌드**
    ```bash
    ./gradlew build
    ```

3. **애플리케이션 실행**
    ```bash
    ./gradlew bootRun
    ```

4. **테스트 실행**
    ```bash
    ./gradlew test
    ```

## API 문서
API 문서는 Swagger를 통해 자동 생성됩니다. 애플리케이션을 실행한 후, 다음 URL에서 Swagger UI를 통해 API 문서를 확인할 수 있습니다:
```
http://localhost:8080/swagger-ui.html
```
