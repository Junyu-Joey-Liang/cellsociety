# Design

## Team 07
#### Team members: Eric Han, Diane Lin, Junyu Liang

## Design goals
what are the project's design goals, specifically what kinds of new features did you want to make easy to add

Flexibility

Communication

Modularity

New features made easy to add
* New simulation type
* New simulation control
* New progress displays (line charts, etc.)
* New cell grid visualizations (cell shapes, etc.)
## High level design
describe the high-level design of your project, focusing on the purpose and interaction of the core classes

###Overview
The project can be divided into three parts: simulation (backend configuration and updates of the grid of cells), configuration
(initializing the simulation with XML files), and visualization (front end display of the simualtion, and also user I/Os)
The main runnable class of the project is MainController, which initiates the main classes for configuration, backend and frontend.

###Simulation

The classes for simulation is contained within the "simulation" diretory.

The main class in simulation is CellGrid. It is an abstract class that is extended for each type of simulation. It maintains a map of all the cell states in the simulation.

The map's keys are coordinates, and the values are a Cell object. The Cell object is also an abstract class extended by each type of simuation.
It keeps track of the current and next state of the cell, the location of the cell, and the neighbors of this cell, and a check() method which implements the update logic by checking neighbor cells.
The CellGrid class also takes care of the update logic for the specific simulation, and takes care of updating each cell during the simulation.

###Configuration

The classes for configuration is contained within the "xml" directory. The main class is the AbstractXm class, which is an abstract class containing general methods
to initialize the configuration map for the specific simulation. It also contains method to create a XML file from the cuurent simulation state in order to save
the currrent status of simulation. The class is extended for each simulation since there is different input simulation parameters for each simulation (e.g. probability of catching fire for Fire, and live span for preditor-prey simulation)

###User Interface

The classes for user interface (visualization, and user controls) is contained in the "userInterface" directory.

The main class is UserInterface. The class intialize and manages all the visual components of the simulation.
It contains a AbstractGridView class, this class maintains the view of the simualtion cell grid view, and the line chart for the states population.
AbstractGridView class is extended to three different classes:

RectangularGridView, TriangularGridView, and HexagonGridView, to realize different cell shape in the simulation.

It is also important to note that the GridView class contains the GridManager class, which contains the CellGrid class from the "simulation" component, and
is responsible for passing update information from backend to frontend, to update the grid display as the simulation goes on. It i also responsible for
passing user input information from the front end to the backend to allow users to dynamically change the simulation.

The UserInterface class also contains a ControlsManager class, which maintains all the controls (buttons, sliders, control grid, text inputs, drop down choices) for the simulation. This includes general controls for all simulations (selectFile, pause, reset, step, speed slider etc)
and also controls specific to each simulation (such as slider to change the probablity to catch fire for Fire simulation). The specific simualtion controls is initialized by the CellGrid class.

## Assumption and Decisions
what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features

Decisions:

**1. Where should the rules be stored? (i.e. which class decides the update logic)**

option 1: Each individual cell class that extends the abstract Cell class are going to have its own rule stored inside it, which will be used in the update() method within that class.
option 2: There's going to be an abstract cellGrid class and the individual grid class that extends it is going to store the rules.


**2. How are we going to see the state of a cell's neighbors?**

option 1: Each cell object will have xLoc and yLoc values. Using that value, we'll determine the state of its neighbors by making a method that looks like (getState(xLoc, yLoc))
option 2: The cellGrid is going to have a field that is a 2D array of cells. Using that, the cellGrid class will see the neighbors of each cell in the 2D array of cells.

**3. which class to maintain CellGrid class**

## New features
describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline


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