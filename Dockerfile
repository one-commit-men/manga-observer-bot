FROM maven:3.3-jdk-8-onbuild
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

RUN chmod +x ./target/bin/main
CMD target/bin/main
