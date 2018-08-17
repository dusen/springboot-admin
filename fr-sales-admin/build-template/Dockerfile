####################################################################################################
#
# Sales Admin Application Dockerfile.
#
# - build
#   docker build -f Dockerfile --build-arg APP_VERSION=${version} .
#
#   - condition for docker build
#     application files must exist in the docker build directory.
#       - sales-batch-${APP_VERSION}.jar
#       - ddl-dependency.properties
#
#   - build options
#     - required
#       --build-arg APP_VERSION=${version} : target batch application version. copy aplication jar files to docker images using this version.
#
# - run
#   docker run \
#       -e RUNENV=<env> -e RUNCOUNTRY=<country> \
#       -e DBMIGRATION=<isDBMigration> \
#       -e JAVA_OPTS=<java opts> \
#       -e DB_URL=<jdbc db access url for write master> \
#       -e DB_USER=<db user for write master> \
#       -e DB_PASSWORD=<db password for write master> \
#       -e S3_BUCKET_NAME=<s3 bucket name> \
#       -e S3_BUCKET_PATH=<s3 bucket path> \
#       -e EDH_INBOUND_BUCKET_NAME=<s3 bucket name using edh inbound interface> \
#       -e EDH_INBOUND_BUCKET_ROOT_PATH=<s3 bucket path using edh inbound interface> \
#       -e TOMCAT_LOGLEVEL=<loglevel for tomcat> \
#       -e SPRING_LOGLEVEL=<loglevel for spring> \
#       -e APPLICATION_LOGLEVEL=<loglevel for application> \
#       -e MYBATIS_LOGLEVEL=<loglevel for mybatis> \
#       <image>
#
####################################################################################################
FROM amazonlinux:2017.12

ARG APP_VERSION=0.1.0

ARG packages="procps java-1.8.0-openjdk-headless"

## Base install
RUN set -x && yum install -y ${packages}

## AP install
RUN set -x && mkdir -p /opt/apps
COPY sales-admin-${APP_VERSION}.jar /opt/apps
COPY ddl-dependency.properties /opt/apps

## Copy AP startup script, ddl(s)
COPY build-template/startup.sh /opt/apps/

RUN set -x && mkdir -p /opt/apps/data/send /opt/apps/data/receive \
           && chmod 755 /opt/apps/startup.sh

## Environment Variables
STOPSIGNAL 15
EXPOSE 8080
CMD ["/opt/apps/startup.sh"]
