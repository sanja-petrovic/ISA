# ISA

Services:
- api: The application
- redis: A key-value database, used in this project for caching
- redis-insight: Redis management GUI
- db: The main PostgreSQL database
- pgadmin: DBMS for PostgreSQL
- kafka & zookeper: Message broker
- kafdrop: Kafka GUI

To run, please follow these steps:
1. `/mvnw clean package -DskipTests` (to build the project .jar file)
2. `docker-compose build` (to build the docker-compose file)
3. `docker-compose run db pgadmin zookeeper kafka kafdrop redis redis-insight -d` (to run everything except the api)
4. Wait about a minute for the database to setup if running the project for the first time 
5. `docker-compose run api` (to run the api)

In case of schema or data changes, please delete the folder "data" inside the folder "postgres", and then run `docker-compose run db` again to rebuild the database with an updated schema.