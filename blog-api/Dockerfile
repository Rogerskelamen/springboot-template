FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/blog-api-1.0-SNAPSHOT.jar /blog_api.jar
ENTRYPOINT ["java", "-jar", "/blog_api.jar", "&"]