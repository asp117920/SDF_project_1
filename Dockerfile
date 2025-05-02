# Base image with Maven and Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Set working directory inside the container
WORKDIR /app

# This command copies all project files from current directory to the container
COPY . .

# This command runs when we make the image of the container, it runs Maven to give us the final JAR file
RUN mvn clean package

# Defines the command you want to learn when when running you container
ENTRYPOINT [ "java", "-jar", "target/aarithmetic-1.0-SNAPSHOT.jar" ]
CMD ["int", "add", "1", "1"]
