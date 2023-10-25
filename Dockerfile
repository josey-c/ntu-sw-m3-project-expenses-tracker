# Use the Eclipse Temurin base image with Java 17
FROM eclipse-temurin:17-jdk-jammy
# Set the working directory
WORKDIR /ntu-sw-m3-project-expenses-tracker
# Copy .mvn directory (if it exists)
COPY .mvn .mvn
# Copy the Maven Wrapper script, pom.xml, and source code
COPY mvnw ./
COPY pom.xml ./
COPY src ./src
# I added this as there are issues with ./mvnw's CRLF for windows, so I had to reformat it.
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw
# Make the Maven Wrapper script executable
RUN chmod +x ./mvnw
# Build and install your application
RUN ./mvnw install
# Define the command to run the Spring Boot application
CMD ["./mvnw", "spring-boot:run"]