# Deploy on digital ocean server
```
ssh root@167.99.196.145
cd website-template/website-template-be
```
* Check if changes to content were made and add them locally
```
git pull
git status
git add *
gitt commit -m "content update"
git push
```
* Connect to Screen
```
# check if any screen session open
screen -ls
#ATACH# to existing screen session with
screen -xS website-template-be
#NEW# screen session creation
screen -S website-template-be

mvn clean install
mvn spring-boot:run

#detach from screen
Ctrl a + Ctrl d
#kill a screen
screen -XS 20411 quit
```

