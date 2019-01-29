FROM openjdk:11
ADD target/rabobank-library.jar docker-rabobank-library.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","docker-rabobank-library.jar"]