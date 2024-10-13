#!/bin/bash

# exit when any command fails
set -e

# allow this script to be run from other locations, despite the
#  relative file paths used in it
if [[ $BASH_SOURCE = */* ]]; then
    cd -- "${BASH_SOURCE%/*}/" || exit
fi

# check options provided
if [ $# -ne 2 ]; then
    >&2 echo "Usage: add-to-catalog.sh <ACCESS_TOKEN> <TOPIC>"
    exit 1
fi

# get the API access token from a command line argument
ACCESS_TOKEN=$1

# get the topic name from a command line argument
TOPIC=$2

# get location of the EEM manager (also useful for checking that oc login has been run)
EEM_API=$(oc get route -n event-automation my-eem-manager-ibm-eem-admin -ojsonpath='https://{.spec.host}')

# retrieve cluster definition
echo "> Retrieving Event Streams cluster information"
(
    set +e

    RESPONSE=$(curl -X GET -s -k \
                --dump-header eem-api-header \
                -H 'Content-Type: application/json' \
                -H "Authorization: Bearer $ACCESS_TOKEN" \
                --output eem-response-clusters.json \
                --write-out '%{response_code}' \
                $EEM_API/eem/clusters)

    if [ $? != 0 ]
    then
        echo " ----------------------------------------------------------------------- "
        echo "ERROR: Failed to connect to Event Endpoint Management"
        exit 1

    elif [[ "$RESPONSE" != 200 ]]
    then
        echo " ----------------------------------------------------------------------- "
        echo "ERROR: Failed to retrieve Event Streams cluster information"
        echo ""
        cat eem-api-header
        exit $RESPONSE
    fi
)

# read the new cluster id into a variable so we can
#  add topics from this cluster
CLUSTERID=$(jq -r '.[] | select(.name == "Event Streams") | .id' eem-response-clusters.json)

# cleanup
rm -f eem-api-header
rm -f eem-response-clusters.json

# add cluster ID to doc
jq --arg clusterId "$CLUSTERID" '.clusterId = $clusterId' doc/$TOPIC.json > eem-event-source.json

RESPONSE=$(curl -X POST -s -k \
            --dump-header eem-api-header \
            -H 'Accept: application/json' \
            -H 'Content-Type: application/json' \
            -H "Authorization: Bearer $ACCESS_TOKEN" \
            --data @eem-event-source.json \
            --output eem-response-new-topic.json \
            --write-out '%{response_code}' \
            $EEM_API/eem/eventsources)
if [[ "$RESPONSE" != \2* ]]
then
    echo " ----------------------------------------------------------------------- "
    echo "ERROR: Failed to submit topic"
    echo ""
    cat eem-api-header
    exit $RESPONSE
fi

EVENTSOURCEID=$(jq -r '.id' eem-response-new-topic.json)

# cleanup
rm -f eem-api-header
rm -f eem-event-source.json
rm -f eem-response-new-topic.json

# prepare a self-service option to publish the topic
jq --arg topic "$TOPIC" --arg eventSourceId "$EVENTSOURCEID" \
   '.alias = $topic | .eventSourceId = $eventSourceId' doc/self-service.json > eem-event-option.json

RESPONSE=$(curl -X POST -s -k \
                --dump-header eem-api-header \
                -H 'Accept: application/json' \
                -H 'Content-Type: application/json' \
                -H "Authorization: Bearer $ACCESS_TOKEN" \
                --data @eem-event-option.json \
                --output /dev/null \
                --write-out '%{response_code}' \
                $EEM_API/eem/options)
if [[ "$RESPONSE" != \2* ]]
then
    echo " ----------------------------------------------------------------------- "
    echo "ERROR: Failed to submit topic option"
    echo ""
    cat eem-api-header
    exit $RESPONSE
fi

# cleanup
rm -f eem-api-header
rm -f eem-event-option.json


echo "Published $TOPIC to Event Endpoint Management"
