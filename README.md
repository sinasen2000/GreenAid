# CSE1105 Group Project

## Quick note before starting: 
As this was a group project, everything wasn't done by me. Here are the things I did myself and contributed.

### My Roles
My teammates told me that I was like the leader of the team as I took part in most of the important decisions and directed & helped my teammates on any kind of problem.

Backend: Along with a friend, I coded the server connection and the functionalities of the application on the backend side. 
Database: I helped the design of the database and wrote a couple of queries using Spring and Hibernate.

### Things done completely by me:
 1. The ReadMe that you are reading now.
 2. The Leaderboard
 3. The Recommendation Tool
 4. Some parts of the server.
 5. Checkstyle

 
### Things I contributed to
 1. A little part of testing
 2. Jacoco/Code Coverage Reports
 3. Scrum Board and Issue Distribution 
 4. Meeting agendas
 5. Front-end 

## Overview
In today's world, the carbon dioxide consumption is increasing day by day. Unfortunately, the daily life became super-dependent on CO2 that it sometimes feels impossible to reverse this situation. But lots of countries and individuals take initiative in order to prevent the devastation that is created by excessive usage of carbon dioxide. Thus, we also wanted to contribute to this movement. As Group 66, our aim is to deliver one of the best applications of whole CSE class to reflect the amount of work we've put in the project.

**GO GREEN** is an application which aims to influence better standards of living by creating an activity-based gamification app. The user registers to the app, selects an activity that he/she's done, selects the amount of it, and the activity gets added to his/her profile and he/she earns XP points according to the amount and type of the activity. Then friends can be added and leaderboards between friends are created in order to influence healthy&sustainable activities even more. 

## Licensing and contributions

1. We used IntelliJ while developing this app. IntelliJ is under the license of Jetbrains.
2. Spring Framework - [Apache 2.0 License](https://choosealicense.com/licenses/apache-2.0/)
3. Hibernate License - [GNU Lesser General Public License v3.0](https://choosealicense.com/licenses/lgpl-3.0/)
4. JavaFXML Scene Builder - [Oracle BSD License](https://www.oracle.com/technetwork/licenses/bsd-license-1835287.html)
5. RecycleCity game - Licensed under US Environmental Protection Agency (US EPA). We e-mailed them for permission to use the game in our application and they granted it.
Proof:
<<img src="/images/mail.jpg">

## Design Goals
 1. Creating a simple, clean, good-looking GUI.
 2. Keeping the code quality up as much as possible. Not writing any spaghetti code.
 3. Writing quality tests and having a sufficient code/test coverage.
 4. Having a user-friendly user-interface.
 5. Having an efficient and clear database that obeys ACID properties.

## Text Files about Project
 1. The Final Report:
 2. Responsible Computer Science Report:
 3. Personal Growth Report:

## Getting started/Installation

### How to import into your IDE

Eclipse:
[http://javapapers.com/java/import-maven-project-into-eclipse/](http://javapapers.com/java/import-maven-project-into-eclipse/)

Intellij:  
[https://www.jetbrains.com/help/idea/2016.2/importing-project-from-maven-model.html](https://www.jetbrains.com/help/idea/2016.2/importing-project-from-maven-model.html)

Go to project's GitLab page [here](https://gitlab.ewi.tudelft.nl/cse1105/2018-2019/oopp-group-66/template)
Click Clone and follow the instructions above to download the repository into your computer.

First, go to src->main->app->Spring Server and run the server. You can also do it via command line by following steps mentioned [here](https://www.maketecheasier.com/run-java-program-from-command-prompt/)
```
After that, make sure that you have JavaFX configured on your IDE. 
Then, go to src->gui->GoGreenGUI and run it by right clicking and selecting 'Run'. Then, you are good to go. 
```

## Getting code coverage reports

**Jacoco**:  
Run `maven install` ([Intellij](https://www.jetbrains.com/help/idea/2016.3/getting-started-with-maven.html#execute_maven_goal)/[Eclipse](http://imgur.com/a/6q7pV))


## Example Code Usage/Screenshots from the app

### Login
<img src="/images/login.jpg">

### Sign-up
<img src="/images/signup.jpg">

### Homepage
<img src="/images/home1.PNG">
<img src="/images/home2.PNG">
<img src="/images/pp.PNG">
<img src="/images/levelup.PNG">


### Friend List
<img src="/images/Friendlist.PNG">
<img src="/images/popup.PNG">

### Leaderboard Page
<img src="/images/fleader.PNG">
<img src="/images/top20.PNG">


### Recommendation/Info page
 <img src="/images/Info.PNG">

### Game Page for kids
<img src="/images/Game.PNG">


## Libraries/Frameworks used
 1. Spring: Java Framework for developing web, mobile and desktop apps and offers lots of ease on server side and database. 
 2. Hibernate: It's used for application-database connection. It is also integrated into Spring.
 3. JavaFX: Used for GUI.

## Tests


**Checkstyle**:  
Run `mvn site`


**JUNIT Test**:
If the test is a JUnit test, you can run the them by simply clicking right to that file and select `Run as Junit test`

Run `mvn test` to create a branch/code coverage.


## Layout of internal code tree

    		SOURCE FOLDER
    	    	    /   \ 
    	           /     \
    	         GUI    APP-->tests for everything
    	                /  \
    	            Client Server-> Main functionalities
    	   		     /         /   \
    	   		    /         /   Communication with database
    	   	Http Requests    /  
    	     Authentication/Authorization

## Database Schema
<img src="/images/db.jpg">


## The Joel Test Score

10/11 

Click [here](https://www.joelonsoftware.com/2000/08/09/the-joel-test-12-steps-to-better-code/) to see what The Joel Test and its result signify.

The Joel Test File: [click here](https://gitlab.ewi.tudelft.nl/cse1105/2018-2019/oopp-group-66/template/blob/master/doc/TheJoelTest.md)
Bugs/Errors Database: [click here](https://docs.google.com/spreadsheets/d/1UN-ubZHSt0T9_UY-3CYMR_IJuuNDgU6KRNW3YR8UtbQ/edit#gid=0)

## Group members

#### Tommaso Tofacchi netID: ttofacchi - 4933249

<img src="/images/tommaso.jpeg">

With the development of our project, I aim to enhance my abilities to cooperate on a team in order to find the best and most efficient solutions to whatever issue we will face.

Maximising group efforts in a smart, intelligent way will be key in achieving this goal, thus I'm looking forward to discovering how we'll be able to optimally distribute the significant workload among each one of us. The presence of deadlines will serve to the purpose of accustoming us to working under pressure, as the necessity to present demos will spur us to constantly remain on track with what should be done.

The final report/project presentation part, furthermore, will greatly help me to maintain the general overview of our product as a whole, plus discussing it and answering questions in front of a panel will challange us to what we could encounter during our present and future working experiences.

#### Sina Sen netID: sinasen - 4821629
<img src="/images/sina.jpg">

Although I have a non-coding background and my programming journey started with the OOP course, I realy enjoy coding and programmig as they are like endless oasises without a limit. In this project, I aim not only to improve my coding and creative thinking skills, I also want to learn how a project team is managed and how different people get along with each other in a long process. In this aspect, I think this project is a nice representation of what's done in real companies: Team up, Think, Design, Code.

To be precise, I want to contribute to the functionality, interface and database parts of the project as I think that I lack some knowledge on these parts and that this project will help me discover tons of new aspects. 

As a team, we have great ideas to realize, and I can't wait to create this application of going green.

#### Julien Lamon netID: jlamon - 4916727
<img src="/images/julien.jpg">

My first steps in coding were only on September, when the bachelor started. However, being a fast learner helped me a lot; I was able to catch up with everything on Java. 

My main goal is pretty basic: seeing how we will be able to manage a project of that size with, I hope, no issues in the communication part! 

My weakest points would be that I am stubborn sometimes, and also that I am impatient. But don't worry, being able to counter these points is part of my main goal.
Also, my strong points would be that I am organised and, as I already said, I am a fast learner.

I hope that, as a team, we will be able to get along with everything, with every deadlines done on time, etc.

The things that I want to improve during these 9 weeks is having the opportunity to touch on new parts of Java. The new parts being the GUI, for example.

#### Warren Akrum netID: wakrum - 4865065
<img src="/images/Warren.jpg">

With this project I aim to improve my programming skills and my ability to work as a team member. I am good at communicating with other team members which will definitely help me cooperate with my fellow team mates.

I am planning on improving my programming by trying to implement ideas that I have for this project with of course the help of other team members. 

With this project I want to learn more about the server and how to contribute more to make an interface, since I think I still lack those capabilities to be able to make a really good interface.

#### Cosmin Octavian Pene - netID: cpene - 4805739
<img src="/images/octavian.jpg">

I was personally waiting for this course since I started my bachelor. I've been involved to some projects in the past, similar to this one let's say, in teams of 5-15 people, and in my opinion this is how you gain a lot of experience in:
1. Programming
2. Teamwork

And these are actually the main points of a good Programmer.

I've started programming 4-5 years ago with C/C++ and since then I experienced some other languages like Java, JavaScript, SQL, HTML, CSS etc. During this proccess of learning I've seen my strong and weak points and of course, my likes and dislikes.

I like programming and especially I like algorithms, functionalities and anything that has to do with the main core of the applicaton. On the other hand, I don't find myself a very creative person in terms of design. I don't personally like to design a webpage, or to make anything look nicer, to improve the visual of an application. I care more about efficiency and functionality, I like to make something work properly, as fast as possible and without bugs. Even though I am not very good in designing an application, I am sure my teammates will help me and we will together develop a really nice application.

My goal is to develop a fully functional Java application, a complete product that can prove me I am on the right way of building a strong career in IT. Moreover, I am also aiming for a very high grade, hopefully a 10, since this is no longer an Exam based course. This course will truly show us our value, our willing to do something in the right way, with strict deadlines and our capability to work proffesionally, in a team.

After I met my teammates I am pretty sure everything will be ok, because I saw everyone really excited about the project itself and I feel each and every one of us will put in a lot of effort. So even though the teamwork is hard in general, I feel it won't be that hard for us.

#### Sven van Collenburg - netID: svancollenburg - 4962435
<img src="/images/sven.jpg">

This project isn't just about programming, it is mostly about being able to work together as a team. 
I am trying to improve my ability to work in a team when working on a shared programming goal as well as improve my general understanding of programming itself.
I think that this project will really help us prepare for programming jobs in the future since this asks far more from every member of the team than ever before.
Getting the team to work together as an efficient machine will be the main task, and i would love to make this possible.
I think i have the creativity and team work capabilities to make a great application, yet i still need to learn a lot about programming itself and more importantly programming in a team.


