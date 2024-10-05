## JPA 활용 배달 플랫폼 백엔드 





#### 도커에 RabbitMQ 설정 
비동기를 위한 Message Queue

RabbitMQ는 오픈 소스 메시지 브로커 소프트웨어

1. 메시지 브로커는 송신자와 수신자 간의 효율적인 메시지 전달을 중개하는 역할을 담당함
2. RabbitMQ는 AMQP(Advanced Message Queuing Protocol)를 기반으로 작동하며, 대규모 분산 시스템에서 사용되는 메시지 큐 서비스를 제공함
3. RabbitMQ는 프로듀서(메시지를 생성하는 애플리케이션)와 컨슈머(메시지를 소비하는 애플리케이션) 간의 비동기적인 통신을 용이하게 함
4. 프로듀서는 메시지를 RabbitMQ에 보내고, RabbitMQ는 이를 큐에 저장함. -> 그런 다음 컨슈머는 큐에서 메시지를 가져와 처리할 수 있음

RabbitMQ는 여러 애플리케이션 간의 통신을 향상시키고, 비동기 처리를 지원하여 시스템의 확장성과 유연성을 높임. 또한, RabbitMQ는 다양한 기능을 제공하여 메시지 라우팅,메시지필터링, 우선순위 지정 등의 작업을 수행할 수 있음

RabbitMQ는 많은 프로그래밍 언어와 통합이 가능하며, 다양한 플랫폼에서 사용할 수 있음. 이를 통해 분산 시스템, 마이크로서비스 아키텍처, 이벤트 기반 시스템 등에서 메시지 기반 통신을 구현할 수 있음

그 외 AMQP 기반 QUEUE
Apache ActiveMQ, Apache Qpid 등 등

확인 법
터미널에서 cd rabbitmq -> docker compose up-d -> 도커에서 rabbitmq 실행 중인지 확인
도커에서 : -> open in terminal 클릭 후  rabbitmq-plugins enable rabbitmq_management 쳐서 실행
