FROM java:8

ADD target/universal/docker-timezone-test.tgz /

WORKDIR /docker-timezone-test/bin
CMD ["./docker-timezone-test"]