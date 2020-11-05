FROM openjdk:14
WORKDIR /app/
COPY ./* ./
RUN javac compiler.java