FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

ENV BOT_TOKEN=[your-token]
ENV DATABASE_URL=[your-url]

RUN mvn install -Dtest=TestManager

WORKDIR target/bin
RUN mkdir /usr/src/disk
VOLUME /usr/src/disk

RUN chmod +x ./main
CMD ./main