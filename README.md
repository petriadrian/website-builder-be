# Deploy on digital ocean server
````
ssh root@167.99.196.145
cd website-builder/website-builder-be
git pull

# check if any screen session open
screen -ls
# atach to existing one with
screen -xS website-builder-be
# create a new screen session 
screen -S website-builder-be

mvn clean install
mvn spring-boot:run

#detach from screen
Ctrl a + Ctrl d
```

