# CS-230-Group-36

[toc]

## Functional requirements

- [ ] tiles
- [ ] rats
- [ ] items
- [ ] win/loss conditions
- [ ] scoring
- [ ] file handling
  - [ ] levels
  - [ ] player profiles
  - [ ] highscore tables
  - [ ] savefiles for the level
    - [ ] manual saves
    - [ ] autosaveing
- [ ] message of the day
- [ ] javafx
  - [ ] start menu
  - [ ] player select
  - [ ] level select
  
## General TODOs

- [ ] fill out the projects tab with what we need to be working on.
- [x] get the initial project set up in this repo
- [ ] make sure everyone is up to speed with using git. (pull requests ect)
- [x] set up unit testing (probbaly JUNIT)
- [ ] design some level files + write up example files
- [ ] unit tests
- [ ] javadoc
- [ ] 

## Using maven on the command line

> you can probably run the project from your ide and skip this noise pretty easily too but if that fails the command line way is guarenteed to work unless the project itself is fucked up so long as you have maven and java installed.

you will need to install maven if you want to use the command line. The easyest way to install maven in via [chocolatey](https://community.chocolatey.org/) once you have chocolatey set up you can the command “choco install maven” (you might have to do this as admin).

if that way is too spooky [here is a guide for the more normal way of installing maven](https://www.javatpoint.com/how-to-install-maven)

> you can probably run the project from your ide pretty easily too but if that fails the command line way is guarenteed to work unless the project itself is fucked up so long as you have maven and java installed.

### Runing the project

the whole project can be built and run with the following comand from the project folder.

```
mvn clean javafx:run
```


### Unit testing
you can run all the project's unit tests together like this.
``` 
mvn test
```
or you just run the tests in a specific test class like this where “TestApp1” and “TestApp2” are the names of test cases in the project.

```
mvn -Dtest=TestApp1,TestApp2 test
```



## Git

here is a short guide on the basics of git https://www.freecodecamp.org/news/learn-the-basics-of-git-in-under-10-minutes-da548267cc91/

### Need to know

- push/pull
- commit
- merge
- checking out branches

### How to work on branches

1. clone the git repo if you havent already
2. checkout the branch you want to work on
3. you can now push any commits you make to the github repo without efecting the main branch
4. once youve finished implementing whatever the separate branch was made for you can make a pull request to merge all your commits into the main branch

### Using git consitently

1. commit each small change with a message describing what youve done (i usualy aim to do this once every 20-40 minutes or so)
2. push changes to the github before you finish for the day

## File formats

### Player Profiles

This file will be very simple as it only needs to store a player name and the max level they have reached

e.g.

a player with name “Toby” who has unlocked level 4 would be represented like this

#### example “profiles.txt”

```
Toby, 1
jan, 2
cham, 3
aldo, 1
jackson, 2
ari, 3
carlos, 1
anthony, 2
```

###  High Score Table

#### example “level1scores.txt”

```

```

### Saved Games

#### example “autosave.txt”

```
```

#### example “examplesavedgame.txt”

```

```

### Level File

The sections are divided up onto different lines except for the last section, which extends from line 6 to the end of the file. Lists are separated by a semicolon (;) while attributes within an item are separated by a comma (,). Level files will be stored as .txt files and named accordingly to the level they represent (Level1.txt = Level 1, Level2.txt = Level 2 etc).

#### Description of individual sections

1. The maximum rat population allowed before the player loses the game, represented as a single natural number.
2. Level time in seconds (bonus points are awarded for each second of game time under this threshold), represented as single natural number.
3. Timing of item spawn rate, represented by a list of natural numbers separated by semicolons.
4. Starting location and sex of all baby rats represented as a list, separated by semi-colons. The attributes of each rat are represented in the form $$x,y,sex;x2,y2,sex2$$ where x and y are the coordinates and sex either M or F to show weather the rat would be male or female.
5. Width and height of the level, represented in the form $$width;height$$ where width and height are both natural numbers.
6. The last section defines the layout of the level itself, each line after this point represents a row in the level. Every row contains a set of tiles which are encoded as G,P or T to represent grass, path and tunnel.

#### Example “level1.txt”

```text
10
120
2;4;8;16;32;64;128
1,1,M;2,3,F;3,2,M;8,1,F
15;7
GGGGGGGGGGGGGGG
GPPPTTPPPTTPPPG
GGGPGGGGPGGPGPG
GPPPGGPPPGGPGPG
GPGGGGGGPGGPGPG
GPPPTTPPPTTPPPG
GGGGGGGGGGGGGGG
```

#### Example “level2.txt”

```

```

#### Example “level3.txt”

```
```

