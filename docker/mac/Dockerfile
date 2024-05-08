FROM openjdk:21

# Gradle 빌드 결과물인 JAR 파일을 지정
ARG JAR_FILE=../../build/libs/*.jar

# JAR 파일을 Docker 이미지 내에 camphub.jar라는 이름으로 복사
COPY ${JAR_FILE} camphub.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "camphub.jar"]