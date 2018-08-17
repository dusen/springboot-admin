#!/bin/bash

# Static Variables
APP_ROOT=/opt/apps
TERM_INTERVAL=5
_RC_WAIT_ERROR=99

##
## Expect Variables
##
##DBMIGRATION=true
##RUNENV=dev
##RUNCOUNTRY=canada
##

# Dynamic Variables
APP_ARCHIVE=`ls -d ${APP_ROOT}/*.jar`
JAVA_HOME=$(dirname $(dirname `readlink -f /usr/bin/java`))

CONTAINER_ID=`cat /proc/self/cgroup | grep "cpu:/" | awk -F / '{print $NF}'`
if [ -z "$CONTAINER_ID" ]; then
    echo "*** ERROR [ Failure to get container id. ] ***"
    exit ${_RC_WAIT_ERROR}
fi


# Set JAVA PATH
export PATH=$JAVA_HOME/bin:$PATH

# Inject Environment Variables
INJECT_ENV="-Dspring.profiles.active=${RUNENV} \
            -Dcountry=${RUNCOUNTRY} \
            -Dservice_name=sales \
            -Dbatch_app_version=v1 \
            -Ddb_user=${DB_USER} \
            -Ddb_password=${DB_PASSWORD} \
            -Ddb_url=${DB_URL} \
            -Dsaml_idp_server_name=${SAML_IDP_SERVER_NAME} \
            -Dsaml_idp_metadata_path=${SAML_IDP_METADATA_PATH} \
            -Dsaml_sp_server_name=${SAML_SP_SERVER_NAME} \
            -Dsaml_sp_https_port=${SAML_SP_HTTPS_PORT} \
            -Demployee_api_host_admin=${EMPLOYEE_API_HOST_ADMIN} \
            -Demployee_api_host_consumer=${EMPLOYEE_API_HOST_CONSUMER} \
            -Demployee_api_version=${EMPLOYEE_API_VERSION} \
            -Demployee_hmac_client_id=${EMPLOYEE_HMAC_CLIENT_ID} \
            -Demployee_hmac_client_secret=${EMPLOYEE_HMAC_CLIENT_SECRET} \
            -Dfluentd_host=${FLUENTD_HOST} \
            -Dfluentd_port=${FLUENTD_PORT} \
            -Dfluentd_tag=${FLUENTD_TAG}.${CONTAINER_ID} \
            -Dsales_hmac_client_id=${SALES_HMAC_CLIENT_ID} \
            -Dsales_hmac_client_secret=${SALES_HMAC_CLIENT_SECRET} \
            -Dsales_api_host_admin=${SALES_API_HOST_ADMIN} \
            -Dsales_api_host_consumer=${SALES_API_HOST_CONSUMER} \
            -Dsales_api_version=${SALES_API_VERSION}"
#            -Dmybatis_loglevel=${MYBATIS_LOGLEVEL} \
#            -Ds3_bucket_name=${S3_BUCKET_NAME} \
#            -Ds3_bucket_path=${S3_BUCKET_PATH} \
#            -Dedh_inbound_bucket_name=${EDH_INBOUND_BUCKET_NAME} \
#            -Dedh_inbound_bucket_root_path=${EDH_INBOUND_BUCKET_ROOT_PATH} \


# Function
CLEAN_UP(){
    echo "`date +\"%F %T.%N\"` {\"RUN\":\"terminate check\"}"
    _CHECK=`ps -ef | grep java | grep -v grep | awk '{print $2}'`
    if [ -z "${_CHECK}" ]; then

        kill ${_CHECK}
        sleep ${TERM_INTERVAL}

         echo "`date +\"%F %T.%N\"` {\"RUN\":\"terminate recheck\"}"
        _RECHECK=`ps -ef | grep java | grep -v grep | awk '{print $2}'`
        if [ -z "${_RECHECK}" ]; then

            kill -9 ${_RECHECK}
        fi
    fi
    exit 0
}

RUN_AP(){
   echo "`date +\"%F %T.%N\"` {\"RUN\":\"$*\"}"
   $*
}

trap CLEAN_UP 15

# Main
RUN_AP ${JAVA_HOME}/bin/java -jar ${INJECT_ENV} ${JAVA_OPTS} ${APP_ARCHIVE}

