FROM oracle/graalvm-ce:19.3.0-java8 as graalvm
#FROM oracle/graalvm-ce:19.3.0-java11 as graalvm # For JDK 11
COPY . /home/app/elastic-client
WORKDIR /home/app/elastic-client
RUN gu install native-image
RUN native-image --initialize-at-build-time=org.apache.http.util.Args,org.apache.http.HttpHost,org.apache.http.util.TextUtils --no-server --static -H:ReflectionConfigurationFiles=build/classes/java/main/META-INF/native-image/elastic/client/reflection-config.json \
     -cp build/libs/elastic-client-*-all.jar


FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/elastic-client/elastic-client /app/elastic-client
ENTRYPOINT ["/app/elastic-client", "-Djava.library.path=/app"]