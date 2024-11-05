API For Test RabbitMQ: localhost:8088/send?message=fail
For this branch:
I make the push message with max message in queue =4, if queue have 4 message, I will push it to DLX(dead-letter-exchange)
For Consumer, I make 2 Consumers for receive message at 2 channels and if success process I will ack, if fail I will set unack