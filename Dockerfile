#FROM fabric8/java-alpine-openjdk8-jre
#ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
#ENV AB_ENABLED=jmx_exporter
#
## Be prepared for running in OpenShift too
#RUN adduser -G root --no-create-home --disabled-password 1001 \
#  && chown -R 1001 /deployments \
#  && chmod -R "g+rwX" /deployments \
#  && chown -R 1001:root /deployments
#
#COPY target/lib/* /deployments/lib/
#COPY target/*-runner.jar /deployments/app.jar
#
#EXPOSE 8099
#
## run with user 1001
#USER 1001
#
#ENTRYPOINT [ "/deployments/run-java.sh" ]

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.1

ARG JAVA_PACKAGE=java-11-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.5

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

# Install java and the run-java script
# Also set up permissions for user `1001`
RUN microdnf install curl ca-certificates ${JAVA_PACKAGE} \
    && microdnf update \
    && microdnf clean all \
    && mkdir /deployments \
    && chown 1001 /deployments \
    && chmod "g+rwX" /deployments \
    && chown 1001:root /deployments \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/lib/security/java.security

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar

EXPOSE 8099
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]