FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip --file pom.xml
FROM openjdk:11-jre
WORKDIR /app
COPY --from=build /app/target/qrscanner-*.jar /app/
EXPOSE 7778

# Allow to override default ENV with container start
ENV JDBC_URI=""
ENV MYSQL_PASSWORD=""
ENV SERVER_TESTMODE=true
ENV MYSQL_ROOT_PASSWORD="12345678"

# Start process inside the container
CMD ["java", "-jar", "./qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar"]