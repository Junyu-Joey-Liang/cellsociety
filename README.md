simulation
====

This project implements a cellular automata simulator.

Names: Eric Han, Diane Lin, Junyu Liang

### Timeline

Start Date: 9/12/2019

Finish Date: 9/22/2019

Hours Spent:

### Primary Roles
Simulation (backend): Eric Han
* Implement a 2D grid of cells that is used to represent the simulation.
* Implement a simulation where the state information of each cell is updated each step based on rules applied to their state and those of their neighbors.
* Implement Java code for the rules of five different simulations: game of life (for testing), segregation, predator-prey, fire, and percolation 

Visualization: Junyu Liang
* Display the current states of the 2D grid and animate the simulation from its initial state until the user stops it.
* Allow users to load a new configuration file, which stops the current simulation and starts the new one.
* Allow users to pause and resume the simulation, as well as step forward through it.
* Allow users to speed up or slow down the simulation's animation rate.

Configuration: Diane Lin
* Read in an XML formatted file that contains the initial settings for a simulation. The file contains three parts:
    * kind of simulation it represents, as well as a title for this simulation and the author of this data file
    * settings for global configuration parameters specific to the simulation
    * width and height of the grid and the initial configuration of the states for the cells in the grid

### Resources Used
https://www2.cs.duke.edu/courses/compsci308/fall19/assign/02_simulation/
https://docs.oracle.com/javase/8/javafx/api/javafx


### Running the Program

**Main class:** 

MainController

**Data files needed:** 
* xml configuration files to initialize the simulation parameters and simulation grid
* MainResources.properties to initialize game constants such as window size etc.

**Interesting data files:**

**Features implemented:** 
* create a simulation cell grid that updates each step based on the previous state
* implemented the five different CA simulations: game of life, segregation, predator-prey, fire, and percolation.
* simulation initial configuration is contained in XML files, and is parsed in by the program to initialize the simulation. The configuration includes:
    * type of simulation
    * shape of the grid cells (currently support rectangle and triangle)
    * dimensions of the simulation grid (number of rows and columns)
    * initial grid setup
* friendly user interface for user to start and control simulation progress
    * grid display
        * display the grid status at each state, the size of each individual cell is determined by xml file's number of row and columns, while the overall size of the grid stays constant for every simulation.
        * 
    * buttons
        * select file: select configuration file that sets up the simulation (if the user choose a new simulation)
        * start: start simulation
        * pause / resume: pause and resume the simulation
        * reset: reset the grid back to blank grid
        * step: step through the simulation step by step
        * speed up & slow down: change simulation animation rate
    * error messages to guide users
    
**Assumptions or Simplifications:**
For the Wa-tor simulation, we assume that the individual sharks and fish don't 'move simultaneously' at each turn (round). This is to prevent two situations
1. A shark eats a fish, but that fish doesn't know that and tries to move to another cell. 
2. A shark/fish moves to a grid, but another cell of the same type simultaneously tries to move to that grid

Thus, the individual cells in the wa-tor simulation have to take turns. However, the sharks will have too much advantage if they can all
move before the fishes can move, and vice versa. We fixed these problems by simplifying the wa-tor simulation so that we shuffle the list
that has all the fish and sharks, and decide each cell's movement by looking at the neighbor cells' future states (nextState).

By using this logic, the cell will not think that its neighbor is a fish/shark if it had already moved or was eaten. It will also be able
to realize when another cell had moved to one of its neighbor grid, which was initially empty. This will eliminate the possibility of any
conflict from simultaneous movements. And as the cells move at a random order, the results will be more 'natural'.

**Known Bugs:**
When the size(# of rows, cols) of the 2D grid of cells gets very big, the simulations sometimes do not work well.
For ant foraging, some cells end up getting larger pheromone values than home/food sources, leading to unwanted results.

**Extra credit:**
* triangular grid cells
* segregation -> possible to initialize the grids randomly according to the amount of empty cells - which can be in the xml - there should be. 

### Notes
* Steps to start a simulation:
    1. Run MainController()
    2. click "select file" to choose an XML file in the xml_files folder
    3. click "start" to start the simulation
    4. During the simulation, you can use "pause", "step", "speed up", "slow down" buttons to control the simulation progress
    5. click "reset" to reset the grid to initial state
    5. click "select file" againt to start a new simulation 

### Impressions
* For this project, our team has a clear delegation of work. This helped to increase efficiency, since everyone is clear about what they need to work on. Also, it reduces many pontential merge conflicts in GIT. 

### Extra Features Added 
* Display a line chart of the populations of all of the "kinds" of cells over the time of the simulation (updated dynamically, similar to the example in predator-prey)
* Each simulation would render a customized panel of controls to allow users to dynamically change simulation parameters
* Users can click on any cell during the simulation to change its state
* Enabled hexagon, triangle, and rectangle cells. 
    * note: for hexagon cells, the grid height is shrinked to keep the hexagon shape
* there is a grid of buttons of the side of rectangular cell grid, the user can select the cells that counts as neighbor, thus changing the game rules dynamically
* Edge types => toroidal, infinite, finite
* Save the current simulation to a new xml file that can be loaded into the program
* Error checking and handling for 
    * invalid/no simulation type given
    * invalid cell location values given
* Implemented Rock paper scissor, and ant-foraging
    * Logic for ant-foraging is completed, but the possible states of the cell is very limited, and there is a bug
    related to storing correct pheromone values. Thus, the ant-foraging looks different from what it should be.
* User can choose the type of neighbor that he/she wants. However, this only works for rectangular grid.
* For each simulation, when the user changes simulation parameters, the configuration is set randomly according to the parameters.
Ant foraging does not have this feature yet.