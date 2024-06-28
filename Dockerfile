FROM openjdk:17-alpine

RUN apk --no-cache add freetype \
    && apk add --no-cache msttcorefonts-installer fontconfig \
    && update-ms-fonts \
    && fc-cache --force

ENV _JAVA_OPTIONS="-Djava.awt.headless=true"

WORKDIR /app

COPY target/trade-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar --server.port=8000"]