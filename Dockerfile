# 사용할 이미지
FROM openjdk:21

# 도커내 작업할 디렉토리
WORKDIR /app

# Gradle 빌드 결과물인 JAR 파일을 지정
ARG JAR_FILE=build/libs/*.jar

# JAR 파일을 Docker 이미지 내에 camphub.jar라는 이름으로 복사
COPY ${JAR_FILE} camphub.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "camphub.jar"]