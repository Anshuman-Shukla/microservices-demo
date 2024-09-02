# microservices-demo

First we have created the product endpoint to send data ,then created a

app-config-data to keep all the config data classes at one point.

Then created a docker-compose folder to run the kafka in docker

        docker-compose -f common.yml -f kafka_cluster.yml up
To check if cluster is read we can use
      kcat -L -b localhost:19092

To concume from kafka        
    kcat -C -b localhost:19092 -t twitter-topic


      check if cluster is ready kafkacat -L -b localhost:19092
2. Kafka-Model we are using Avro: To create the model using AVro 
we need to define a schema in resource >avro in kafka-model ,when be compile it automatically create the Model for us.

3. Kafka-admin it will create the topic ,check for topic and schema.
    
4. common-config to add common config that can use across the module.
5. RetryConfigData class in app-config-data
6. Create a kafka adminconfig and webclientconfig and kafkaAdmin client.
7. Commented in KafkaProducerConfig as snappy compression is not compatable with mac.
   //props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.getCompressionType());
8. Dockerization is Pending (Need to add the maven plugin to support Dockerization. SECTION 3 Lecture 20)
9. Create a directory name config-server-repository
10. add the yml file for the configuration and use git init and add all file using git add . and commit the changes using git commit
-am "{message}"
11. added the kafka-consumer service.
12. Added the elastic in docker compose
13. 