Code from AStar implementation project for artificial intelligence class. Files written: 
	EAStarExp.java -> Astar for exp cost
	DAStar_Div.java -> Astar for div cost
	DijkstraAI.java -> basic dijkstra implementation




Compiles source
  987  javac  Src/*.java

Runs randomly generated map
  977  java Main StupidAI

Runs with map generated from DEM2XYZ
  985  java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI
  991  time java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI

Runs multiple modules
  992  time java -Xmx1024M Main -load MTAFT.XYZ DijkstraAI DirectAI

Runs with a specifiic seed
   java Main StupidAI -seed 1   ***note no equal sign***

