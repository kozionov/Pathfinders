docker build -t kozionov/pathfinders .
docker login
docker push kozionov/pathfinders


VM: 4868090e-f0a5-11ee-bc67-d00d8278e730
Login: ubuntuuser
Password: 12yoLAsr68UL
ssh 81.94.150.210

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