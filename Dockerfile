FROM maven:3.3-jdk-8-onbuild
WORKDIR target/bin
RUN chmod +x ./main
CMD ./main
