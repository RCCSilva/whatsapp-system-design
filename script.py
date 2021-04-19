from kafka import KafkaProducer
import json

producer = KafkaProducer(
    value_serializer=lambda m: json.dumps(m).encode('ascii')
)

# producer.send('session.gateway.set', {
#     'user_id': 10,
#     'gateway': '168f3321'}
# )
producer.send('session.message.create', {'from': 1, 'to': 10, 'text': 'Text me!', 'time': '22:11'})

producer.flush()