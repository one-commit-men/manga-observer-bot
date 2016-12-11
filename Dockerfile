FROM maven:3.2-jdk-7-onbuild
WORKDIR target/bin
CMD ./main
