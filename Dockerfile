FROM maven:3.3-jdk-8-onbuild
WORKDIR /usr/src/app
RUN cd target/bin
RUN chmod +x ./main
CMD ./main
