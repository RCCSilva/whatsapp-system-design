from kafka import KafkaConsumer
import json

consumer = KafkaConsumer(
    'gateway.asd.message.send',
    value_deserializer=lambda m: json.loads(m.decode('ascii'))
)

for m in consumer:
    print(dir(m), m.headers)
    print(m)