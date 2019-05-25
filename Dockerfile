FROM maven:3.5-jdk-8 AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

# Base Alpine Linux based image with OpenJDK JRE only
#FROM openjdk:8-jre-alpine
# copy application WAR (with libraries inside)
#COPY target/spring-boot-*.war /app.war
# specify default command
#CMD ["/usr/bin/java", "-jar", "/app.war"]