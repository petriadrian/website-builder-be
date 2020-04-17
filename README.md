# Deploy on digital ocean server
````
ssh root@167.99.196.145
cd website-builder/website-builder-be
git pull

# check if any screen session open
screen -ls
#ATACH# to existing screen session with
screen -xS website-builder-be
#NEW# screen session creation
screen -S website-builder-be

mvn clean install
mvn spring-boot:run

#detach from screen
Ctrl a + Ctrl d
#kill a screen
screen -XS 20411 quit
```

