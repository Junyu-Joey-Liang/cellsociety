# DESIGN EXERCISE

## Discussion

### Core classes
We plan to include the following core classes:
Main 
Grid
Cell (abstract) [extended for each type of simulation]
Visualization
![](https://i.imgur.com/L3y4pS5.png)


### Design decisions:
**1. Where should the rules be stored? (i.e. which class decides the update logic)**

option 1: Each individual cell class that extends the abstract Cell class are going to have its own rule stored inside it, which will be used in the update() method within that class.
option 2: There's going to be an abstract cellGrid class and the individual grid class that extends it is going to store the rules.


**2. How are we going to see the state of a cell's neighbors?**

option 1: Each cell object will have xLoc and yLoc values. Using that value, we'll determine the state of its neighbors by making a method that looks like (getState(xLoc, yLoc))
option 2: The cellGrid is going to have a field that is a 2D array of cells. Using that, the cellGrid class will see the neighbors of each cell in the 2D array of cells.

___

## Example Code for Different Use Cases
- Apply the rules of the middle cell: set the next state of a cell to dead by counting its number of neightbors using the game of life rules for a cell in the middle
    - Loop 1: The grid cell would iterate through every cell in its collection, and call the check() method within each cell. The cell would check its neighbors, and decide its next state. If the cell needs to be dead in the next loop, its nextState will be 'dead'.
    - Loop 2: The grid cell would loop again, and call update() method in each cell. The cell would switch to the next state that was determined in Loop 1. 
    
- Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the game of life rules for a cell
    - In a cell's update() method, we get the state of the cells around that cell and use that to determine the next step.
    - The edge cells are going to be...((xLoc==0 || xLoc==grid.xLength - 1) || (yLoc==0 || yLoc == grid.yLength - 1)
    - The getState(int xLoc, int yLoc) that's going to be used to get state of the neighbors' state
    - If xLoc or yLoc used to call getState is out of the range of the grid, getState will instantly return a value that means the cell's state is dead.
    - Only the states of the actual neighbors will be counted and if the rule for the cell is satisfied, the next state of the edge cell will be 'live'.
- Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    - The grid will iterate through each cell within the grid. Each cell will check its neighbors. 
    - If there are 3 neighbors that are alive(rules may change, and the information will be in each cell class), the cell's needsUpdate parameter will remain false. 
    - If there are less than 3 neighbors that are alive, the cell's needUpdate parameter will be updated to true and the cell will change to dead (having its color changed) when the update() method is called. After the grid has iterated through each cell within the grid, the new frame will be added to the scene.
- Set a simulation parameter: set the value of the parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
    - The parameter will be parsed by the Main class when configuring the simulation. It will be passed as a parameter to construct the Fire Cells. 
- Switch simulations: use the GUI to change the current simulation from the Game of Life to Wator
    - Each type of simulation has its own Cell class that extends the abstract Cell class.
    - When switching simulations, the Grid class (which contains a collection of all cells in the simulation) will remove every single cell it holds, and initialize by refilling itself with new cell instances of the cell class for the new type of simulation.