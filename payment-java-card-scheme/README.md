
### Kafka manual tests

`brew install kafkacat`

`echo "{\"id\": 345,\"issuerId\": \"issuer example\",\"acquirerId\": \"acquirer example\",\"amount\": 20001.99}" \
| kcat -b localhost:9093 -H "messageType=TRANSFER_ENTRY" -t card-scheme-transaction`

`kcat -C -b localhost:9093 -t testTopic`


### How to run

`docker build -t card-scheme .`

`docker run  --net="host" -it card-scheme`