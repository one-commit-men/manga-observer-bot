FROM maven:3.3-jdk-8-onbuild
WORKDIR target/bin
RUN cd target/bin && chmod +x ./main
CMD ./main
