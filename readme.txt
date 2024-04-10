docker build -t kozionov/pathfinders:v0.0.1 .
docker login
docker push kozionov/pathfinders:v0.0.1

docker ps
docker ps -a
docker stop pathfinders
docker rm pathfinders
docker pull kozionov/pathfinders:v0.0.1


VM: 4868090e-f0a5-11ee-bc67-d00d8278e730
Login: ubuntuuser
Password: 12yoL-Asr-68UL
ssh ubuntuuser@81.94.150.210

Установка докер https://selectel.ru/blog/docker-install-ubuntu/


sudo docker run --name pathfinders -d -p 8080:8080 kozionov/pathfinders - запуск контейнера

docker-compose up -d

Создать папку на диске
docker volume create --driver local --opt type=none --opt device=/mnt/docker/volumes/mysql-database --opt o=bind mysql-database
docker volume create --driver local --opt type=none --opt device=C:/test/mnt/docker/volumes/mysql-database --opt o=bind mysql-database


Использовать эту папку
volumes:
  database:
    name: mysql-database
    external: true
---------------------------------------------
БД:
docker run --name database -p 5432:5432 -e POSTGRES_USER=pathfinder -e POSTGRES_PASSWORD=pathfinder -e POSTGRES_DB=pathfinder -d postgres:13.3
docker run --name habr-pg-13.3 -p 5432:5432 -e POSTGRES_USER=habrpguser -e POSTGRES_PASSWORD=pgpwd4habr -e POSTGRES_DB=habrdb -e PGDATA=/var/lib/postgresql/data/pgdata -d -v "/absolute/path/to/directory-with-data":/var/lib/postgresql/data postgres:13.3

---docker-compose
version: "3.9"
services:
  postgres:
    image: postgres:14.8-alpine3.18
    environment:
      POSTGRES_DB: "habrdb"
      POSTGRES_USER: "habrpguser"
      POSTGRES_PASSWORD: "pgpwd4habr"
      PGDATA: "/var/lib/postgresql/data/pgdata"
    volumes:
      - psgr-database:/var/lib/postgresql/data
    ports:
      - "5432:5432"

volumes:
  psgr-database:

docker volume create --driver local --opt type=none --opt device=/home/volumes/psgr-database --opt o=bind psgr-database

---------------------------
Network:
docker network create network-example
docker network create my-network

services:
  my_app:
    image: "web-app:latest"
    container_name: web-app
    ports:
      - "8080:8080"
    networks:
      - network-example

networks:
  network-example:
    external: true
------------------------------------------------------------------------------

docker-compose -f docker-compose-psgr.yml up -d
docker-compose -f docker-compose-app.yml up -d

docker network create my-network
docker network inspect my-network

docker volume create --driver local --opt type=none --opt device=/mnt/docker/volumes/my_volume --opt o=bind my_volume