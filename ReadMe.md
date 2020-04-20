## AStar Implementation (in Java)
#### by David Cerda

### Objective
Implement a AStar as part of a project for arificial intelligence for the module on informed search. 

### 
Code from AStar implementation project for artificial intelligence class. Files written: 
	EAStarExp.java -> Astar for exp cost
	DAStar_Div.java -> Astar for div cost
	DijkstraAI.java -> basic dijkstra implementation




> Compiles source
>> 987  javac  Src/*.java  

<br/>

> Runs randomly generated map
>>  977  java Main StupidAI

<br/>

> Runs with map generated from DEM2XYZ
>>  985  java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI
>> 991  time java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI

<br/>

> Runs multiple modules
>>  992  time java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI DirectAI

<br/>

> Runs with a specifiic seed
>> java Main StupidAI -seed 1   ***note no equal sign***

