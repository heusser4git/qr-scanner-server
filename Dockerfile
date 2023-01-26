# Base image, "build" is the alias of this first step
FROM maven:3.6.3-jdk-11 as build

# Set dir inside container
WORKDIR /app

# Copy all files from location of Dockerfile into current directory (/app) in container
COPY . .

# Build maven project, creates jar file in "target" dir
RUN mvn clean package -Dmaven.test.skip --file pom.xml

# Base image
FROM openjdk:11-jre

# Set dir inside the container
WORKDIR /app

# Copy built jar file from previous "build" step to app dir
COPY --from=build /app/target/qrscanner-*.jar /app/

# Make port accessible from outside of the container
EXPOSE 7778

# Allow to override default JDBCI_URI with container start
ENV JDBC_URI=""

# Start process inside the container
CMD ["java", "-jar", "./qrscanner-1.0-SNAPSHOT-jar-with-dependencies.jar", "--test=true"]



# docker build -t qrscanner-server --quiet .
# docker run -d --name qrscanner-server -p 7778:7778 qrscanner-server

# docker tag qrscanner-server:latest tcodemalans/qrscanner-server:latest
# docker login --username tcodemalans --password <password>
# docker push tcodemalans/qrscanner-server:latest