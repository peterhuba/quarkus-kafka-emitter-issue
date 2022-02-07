# Quarkus reproducer app for issue "Unable to connect an emitter with the channel"

To run:
* run `docker-compose up` in directory `src/main/db/`
* either start external Kafka cluster with topics `topic.operation-context, topic.operation-status
 topic.operation-unit-status` created, or set `quarkus.kafka.devservices.enabled` to true
* run `quarkus dev`