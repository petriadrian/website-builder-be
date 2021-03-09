provision:
	scp ./docker-compose.yml TOKEN.txt digital-ocean-petri:~

login:
	cat ./TOKEN.txt | docker login https://docker.pkg.github.com -u petriadrian --password-stdin

build:
	docker-compose build website-builder-be

push:
	docker-compose push website-builder-be

deploy: build push
	ssh digital-ocean-petri "docker-compose pull && docker-compose up -d"
