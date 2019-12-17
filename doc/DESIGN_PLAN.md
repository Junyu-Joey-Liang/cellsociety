# Cell Society: Design Plan

## Team members
Diane Lin
Eric Han
Junyu Liang

## Specification

### Introduction
The goal of the project is to create a program that can run the five CA simulations (and can be easily extended to other types of CA simulations). The initial status of the simulation will be read from a XML file that specifies the simulation's configuration.

Primary design goals:

* The simulation should be easy to extend to other types of CA simulations

* The user interface should provide clear and easy access for the user to select different simulations, change simulation display (e.g. change speed, change display mode from flow to step by step)

* The user should be able to choose the simulation, and the right XML will have to be read to make the correct configuration of cells.


Primary architecture:

* There is going to be a cellGrid class, which represents the grid of cells. It is going to hold all the cells that is supposed to be in the grid. We made this class to keep the individual cells hidden from the main class so that it cannot directly access the cells.

* The cell is going to be an abstract super class. It is going to have some abstract methods that the objects of cell's children must override, such as the method for updating the cell's status.

* There are going to be individual cell classes (GameOfLifeCell, PercolationCell, etc) that extend the cell class. Each is going extend the abstract methods its own set of rules that each simulation corresponding to the class should have. The cellGrid is merely going to call the methods that the abstract cell class has. How the individual cells are going to implement that will be hidden. As these codes of the cellGrid will depend on the abstract cell class, the code will also be more flexible and easy to change.

### Overview
![](https://i.imgur.com/xFQxDYz.png)

Notes:
* Cell Class: abstract class that would be extended for each different simulations. Specifically, the check method would be overriden for different rules. And the status types would be different for each simulation.

The simulation program flow would look like this:git 
![](https://i.imgur.com/5pzoJnD.png)



### User Interface

The users would interact with the program mainly by using mouse input. There will be several buttons on the UI for User actions:
* choose simulation config file (XML)
* start simulation
* pause simulation
* step through simulation 
* change speed of simulation

Also, if the user chooses to run the simulations step by step, they would move forward to next steps by pressing [Right] on the keyboard.

**User Interface initial design:**
![](https://i.imgur.com/4rq0MnO.png)

**Erroneous situations:**
1. config file not found (bad input)
2. invalid grid (the setup does not match the dimensions specified)
3. unsupported simulation type
4. simulation title is too long that it does not fit on the screen
5. Edge cells may try to read state from cells that do not exist.



### Design Details
The team debated about whether to use a 2D arrayList or a HashMap to represent the cell grid. One advantage of using the HashMap over the 2D array is the HashMap is not initialized/constrained to a certain number of cells. More cells can be added to the HashMap as needed. As oppoised to the 2D arrayList, we would need to initialize the arrayList to contain a set number of cells. 

Additionally, the team debated about how we wanted to update the cell. We considered having the update method (the method that determined the next state of the cell) placed in the cellGrid class. This implementation would allow us to easily access the grid (which holds the cell objects) and check if a specific cell needs to be updated depending on the neighboring cells. We decided against this idea because we felt that the cell should be responsible for updating itself. It should not be the grid's job to update each cell because the update method within the grid would have to change depending on the simulation we are running. We decided, instead, to have the cell be responsible for updating itself. To implement this, the update method, which will be tied to the cell object, will be called within the grid class. To gain access to the neighboring cells, the parameters of the update method will be an arrayList of cell objects that are the surrounding neighbors of the cell that is checking itself to see if it needs to be updated. Since we are making an abstract cell class and creating specific cell subclasses that is specific to each simulation, this will allow us to create update methods that are specific to each simulation. 

### Team responsibilities
The configuration part, in which the XML files are read and the initial states and locations of the cells are decided, will be done by Diane Lin. Junyu Liang is going to work on the User Interface, and work on the part that reads the user'sactions and makes the program take approprate action. Erie Seong Ho Han is going to work on the simulation part and work on designing the classes for the cells and cellgrid in order to make the cells update their state correctly. 


___

## Use Cases

- Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    - Loop 1: The grid cell would iterate through every cell in its collection, and call the check() method within each cell. The cell would check its neighbors, and decide its next state. If the cell needs to be dead in the next loop, its nextState will be 'dead'.
    - Loop 2: The grid cell would loop again, and call update() method in each cell. The cell would switch to the next state that was determined in Loop 1. 

- Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
    - In a cell's update() method, we get the state of the cells around that cell and use that to determine the next step.
    - The edge cells are going to be...((xLoc == 0 || xLoc == grid.xLength - 1) || (yLoc == 0 || yLoc == grid.yLength - 1)
    - The getState(int xLoc, int yLoc) that's going to be used to get state of the neighbors' state
    - If xLoc or yLoc used to call getState is out of the range of the grid, getState will instantly return a value that means the cell's state is dead.
    - Only the states of the actual neighbors will be counted and if the rule for the cell is satisfied, the next state of the edge cell will be 'live'.

- Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    -  The grid will iterate through each cell within the grid. Each cell will check its neighbors. 
    - If there are 3 neighbors that are alive(rules may change, and the information will be in each cell class), the cell's needsUpdate parameter will remain false. 
    - If there are less than 3 neighbors that are alive, the cell's needUpdate parameter will be updated to true and the cell will change to dead (having its color changed) when the update() method is called. After the grid has iterated through each cell within the grid, the new frame will be added to the scene.
- Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
    -  The parameter will be parsed by the Main class when configuring the simulation. It will be passed as a parameter to construct the Fire Cells. 
- Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
    -  Each type of simulation has its own Cell class that extends the abstract Cell class.
    - When switching simulations, the Grid class (which contains a collection of all cells in the simulation) will remove every single cell it holds, and initialize by refilling itself with new cell instances of the cell class for the new type of simulation.
