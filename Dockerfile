FROM openjdk:11-jre-slim-buster

RUN apt-get update && apt-get -y install procps net-tools vim curl less wget

RUN echo 'alias ll="ls -alF"' >> ~/.bashrc

RUN mkdir -p /app

RUN mkdir -p src/main/resources/data/

#COPY download_jar.sh .

COPY hack2023-0.0.1-SNAPSHOT.jar /app/.

EXPOSE 8080/tcp

CMD ["tail","-f","/dev/null"]

CMD ["java","-jar","app/hack2023-0.0.1-SNAPSHOT.jar"]