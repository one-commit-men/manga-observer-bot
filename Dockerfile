FROM maven:3.3-jdk-8-onbuild
WORKDIR target/bin
RUN mkdir /usr/src/disk
VOLUME /usr/src/disk

RUN chmod +x ./main
CMD ./main