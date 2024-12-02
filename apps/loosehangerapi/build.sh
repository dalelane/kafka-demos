#!/bin/sh

# build the demo app in a container image, and push it to the image registry
#  in the OpenShift cluster where the demo will run

# log in to the container registry
export REGISTRY=`oc get route default-route -n openshift-image-registry --template='{{ .spec.host }}'`
# docker login -u `oc whoami` -p `oc whoami --show-token` ${REGISTRY}

# build the demo application
mvn clean package liberty:package

# build and push the container
docker build -t ${REGISTRY}/event-automation/loosehanger-api:0.1.1 .
docker push ${REGISTRY}/event-automation/loosehanger-api:0.1.1