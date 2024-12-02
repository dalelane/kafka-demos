FROM --platform=linux/amd64 icr.io/appcafe/open-liberty:kernel-slim-java17-openj9-ubi

COPY --chown=1001:0 \
    src/main/liberty/config \
    /config/

RUN features.sh

COPY --chown=1001:0 \
    target/retail-rest-apis.war \
    /config/apps

RUN configure.sh
