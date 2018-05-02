pwd=$(shell pwd)
home=$(HOME)

clean-containers:
	docker-compose kill
	docker-compose rm -f

wait-for-mysql:
	docker run --rm --network 'testingwithdocker_default' busybox /bin/sh -c "until nc -z db_server 3306; do sleep 3; echo 'Waiting for DB to come up...'; done"

start-containers: clean-containers
	docker-compose up -d
	make wait-for-mysql

prepare-database: start-containers
	docker-compose exec db_server /bin/sh -c "mysql -u root -proot testing_with_docker_db < /scripts/create_tables.sql"

integration-test: prepare-database
	docker run --rm -v $(pwd):/project -v $(home)/.ivy2:/root/.ivy2 -v $(home)/.sbt:/root/.sbt -w /project --network 'testingwithdocker_default' hseeberger/scala-sbt sbt it:test
	make clean-containers
