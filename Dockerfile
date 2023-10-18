FROM openjdk:17-oracle

WORKDIR /app

COPY ./target/expenses-tracker-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java","-jar","expenses-tracker-0.0.1-SNAPSHOT.jar"]
