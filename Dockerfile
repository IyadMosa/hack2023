FROM maven:3.8.2-jdk-11-slim AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

FROM openjdk:11-jre-slim-buster

WORKDIR /app

RUN apt-get update && apt-get -y install procps net-tools vim curl less wget

RUN echo 'alias ll="ls -alF"' >> ~/.bashrc

COPY --from=builder /app/target/hack2023-0.0.1-SNAPSHOT.jar /app/.

EXPOSE 8080/tcp

CMD ["tail","-f","/dev/null"]

CMD ["java","-jar","app/hack2023-0.0.1-SNAPSHOT.jar"]