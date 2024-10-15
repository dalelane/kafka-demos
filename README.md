# Event Automation demos

I use https://github.com/IBM/event-automation-demo to setup demos that I give using IBM Event Automation. But sometimes it helps to have additional streams of events to use to illustrate other event-driven ideas. This repo contains the setup I am currently using for that.

## Pre-requisites

1. Create the default IBM Event Automation demo environment
    - Use the instructions [here](https://github.com/IBM/event-automation-demo/blob/main/INSTALL-OPTIONS.md) (choosing one of the options with persistent storage)
2. Seed the Event Endpoint Management catalog
    - Use the instructions [here](https://github.com/IBM/event-automation-demo/tree/main?tab=readme-ov-file#step-2---populate-the-catalog)
3. Modify the Kafka Connect deployment to add additional connectors
    ```sh
    oc apply -f mqtt-credentials.yaml
    oc apply -f kafka-connect.yaml
    ```

## Instructions

### MQTT updates

1. Create the topics
    ```sh
    oc apply -f mqtt-topics.yaml
    ```
2. Create the connectors
    ```sh
    oc apply -f mqtt-connectors.yaml
    ```

**To demonstrate the source connector:**
- publish MQTT messages to [HiveMQ MQTT broker](https://www.mqtt-dashboard.com/) using the `/eventautomation/demo/source` topic
- verify Kafka messages on Event Streams on the `MQTT.SOURCE` topic

**To demonstrate the sink connector:**
- produce JSON Kafka messages to the `MQTT.SINK` topic on Event Streams
- verify MQTT messages from [HiveMQ MQTT broker](https://www.mqtt-dashboard.com/) using the `/eventautomation/demo/sink` topic

---

### Social media updates

1. Create an access token at an instance of Mastodon, including the following scopes
    - `read:statuses`
    - `read:search`
2. Update the [`mastodon-credentials.yaml`](./mastodon-credentials.yaml) file, with the access token and the hostname for the instance you chose
3. Create the credentials and topics
    ```sh
    oc apply -f mastodon-credentials.yaml
    oc apply -f mastodon-topics.yaml
    ```
4. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-mastodon` section in `.spec.externalConfiguration`
5. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
6. Create the connectors
    ```
    oc apply -f mastodon-connectors.yaml
    ```

**To demonstrate:**
- post updates to Mastodon using the #netflix or #xbox hashtag
- verify Kafka messages on Event Streams on the `MASTODON.NETFLIX` or `MASTODON.XBOX` topics

**To add the topics to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  MASTODON.NETFLIX
./add-to-catalog.sh  <EEM ACCESS TOKEN>  MASTODON.XBOX
```

---

### Stock trading events

1. Create [a free API key](https://www.alphavantage.co/support/#api-key)
2. Update the [`stocktrades-credentials.yaml`](./stocktrades-credentials.yaml) file
3. Create the credentials and topics
    ```sh
    oc apply -f stocktrades-credentials.yaml
    oc apply -f stocktrades-topics.yaml
    ```
4. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-stocktrades` section in `.spec.externalConfiguration`
5. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
6. Create the connectors
    ```
    oc apply -f stocktrades-connectors.yaml
    ```

**To demonstrate:**
- verify Kafka messages on Event Streams on the `STOCKTRADES.IBM`, `STOCKTRADES.MICROSOFT`, `STOCKTRADES.SALESFORCE`, `STOCKTRADES.GOOGLE` topics

**To add the topics to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  STOCKTRADES.IBM
./add-to-catalog.sh  <EEM ACCESS TOKEN>  STOCKTRADES.MICROSOFT
./add-to-catalog.sh  <EEM ACCESS TOKEN>  STOCKTRADES.SALESFORCE
./add-to-catalog.sh  <EEM ACCESS TOKEN>  STOCKTRADES.GOOGLE
```

---

### Wikipedia edits

1. Create the topic
    ```sh
    oc apply -f wikipedia-topic.yaml
    ```
2. Create the connector
    ```sh
    oc apply -f wikipedia-connector.yaml
    ```

**To demonstrate:**
- verify Kafka messages on Event Streams on the `WIKIPEDIA` topic

**To add the topic to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  WIKIPEDIA
```

---

### PostgreSQL updates

1. Create the PostgreSQL database
    - Use the instructions [here](https://github.com/IBM/event-automation-demo/blob/main/INSTALL-OPTIONS.md#postgresql)
2. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-postgresql` section in `.spec.externalConfiguration`, **and** the `pgsqldemo-cluster-cert` section in `.spec.tls`
3. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
4. Create the topics
    ```sh
    oc apply -f postgresql-topics.yaml
    ```
5. Create the connector
    ```sh
    oc apply -f postgresql-connector.yaml
    ```

**To demonstrate:**
- insert/update/delete rows into the `buildings` or `warehouses` tables in the pgsqldemo database

**To add the topics to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  pgsqldemo.public.buildings
./add-to-catalog.sh  <EEM ACCESS TOKEN>  pgsqldemo.public.warehouses
```


---

### Weather updates

1. Create [a free API key](https://openweathermap.org/api)
2. Update the [`weather-credentials.yaml`](./weather-credentials.yaml) file
3. Create the credentials and topics
    ```sh
    oc apply -f weather-credentials.yaml
    oc apply -f weather-topics.yaml
    ```
4. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-stocktrades` section in `.spec.externalConfiguration`
5. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
6. Create the connectors
    ```
    oc apply -f weather-connectors.yaml
    ```

**To demonstrate:**
- verify Kafka messages on Event Streams on the `WEATHER.HURSLEY`, `WEATHER.ARMONK` topics

**To add the topics to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  WEATHER.HURSLEY
./add-to-catalog.sh  <EEM ACCESS TOKEN>  WEATHER.ARMONK
```

---

### JDBC sink

1. Create the PostgreSQL database
    - Use the instructions [here](https://github.com/IBM/event-automation-demo/blob/main/INSTALL-OPTIONS.md#postgresql)
2. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-postgresql` section in `.spec.externalConfiguration`, **and** the `pgsqldemo-cluster-cert` section in `.spec.tls`
3. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
4. Create the topic
    ```sh
    oc apply -f pgsqljdbc-topic.yaml
    ```
5. Create the connector
    ```sh
    oc apply -f pgsqljdbc-connector.yaml
    ```

**To demonstrate:**
- send Kafka JSON messages to the `pgsqljdbcsink` topic:
```sh
curl \
    --silent -k \
    -X POST \
    -H "Content-Type: application/json" \
    --data @pgsqljdbc-message.json \
    -u kafka-demo-apps:$(oc get secret kafka-demo-apps -nevent-automation -ojsonpath='{.data.password}' | base64 -d) \
    https://$(oc get eventstreams my-kafka-cluster -o jsonpath='{.status.routes.recapi-external}')/topics/pgsqljdbcsink/records > /dev/null
```

_Note: Enable the REST Producer on the Event Streams cluster by adding `restProducer: {}` to `spec`._

**To add the topic to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  pgsqljdbcsink
```

---

### Xbox updates

1. Create [a free API key](https://xbl.io/)
2. Update the [`xbox-credentials.yaml`](./xbox-credentials.yaml) file
3. Create the credentials and topics
    ```sh
    oc apply -f xbox-credentials.yaml
    oc apply -f xbox-topics.yaml
    ```
4. Update [`kafka-connect.yaml`](./kafka-connect.yaml) to un-comment the `connect-creds-xbox` section in `.spec.externalConfiguration`
5. Apply the updated Kafka Connect spec
    ```sh
    oc apply -f kafka-connect.yaml
    ```
6. Create the connector
    ```
    oc apply -f xbox-connector.yaml
    ```

**To demonstrate:**
- verify Kafka messages on Event Streams on the `XBOX.ACHIEVEMENTS`, `XBOX.PRESENCE`, `XBOX.TEXTPOSTS`, `XBOX.USERPOSTS` topics

**To add the topics to Event Endpoint Management:**
```sh
./add-to-catalog.sh  <EEM ACCESS TOKEN>  XBOX.ACHIEVEMENTS
./add-to-catalog.sh  <EEM ACCESS TOKEN>  XBOX.PRESENCE
```

---
