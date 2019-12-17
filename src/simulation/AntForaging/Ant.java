package simulation.AntForaging;

import simulation.GridInfo;
import simulation.Individual;
import utils.Point;

import java.util.ArrayList;
import java.util.List;

public class Ant extends Individual{

    private static final GridAttribute HOMEPHEROMONE = GridAttribute.HOMEPHEROMONE;
    private static final GridAttribute FOODPHEROMONE = GridAttribute.FOODPHEROMONE;
    private static final int LIFETIME = 500;
    private static final double K = 0.001;
    private static final double N = 3;

    private int curDirection = -1;
    private List<Point> myDirections = new ArrayList<>();
    private int lifeTime = LIFETIME;
    private int survivedTime = 0;
    private boolean hasFoodItem;

    public Ant(GridInfo gridInfo, List<Point> orderedDirections) {
        super(gridInfo);
        myDirections.addAll(orderedDirections);
        //if this is initialized as false, it will change this state to true inside
        //its home.
        hasFoodItem = false;
    }

    @Override
    public void move() {
        dropPheromone();  //drops pheromone.
        List<Point> directions;
        // cannot move if all around are obstacles.
        if(possibleDirectionsToMove(getAllNeighbors()).isEmpty()) {
            setMoved(false);
        }
        else {
            if (curDirection == -1) {
                directions = possibleDirectionsToMove(getAllNeighbors());
            } else {
                if (possibleDirectionsToMove(getInitialThree()).isEmpty()) {
                    directions = possibleDirectionsToMove(getRestOfDirections());
                } else {
                    directions = possibleDirectionsToMove(getInitialThree());
                }
            }
            changeGridInfo(destinationDirection(directions));
        }
        dropFood();
        System.out.println(hasFoodItem);
        survivedTime++;
        if(lifeTime < survivedTime) {
            setDead(true);
        }
    }

    private void changeGridInfo(Point direction) {
        curDirection = myDirections.indexOf(direction);
        setMoved(true);
        GridInfo destinationGridInfo = getMyGridInfo().getOneNeighborGrid(direction);
        getMyGridInfo().addRemovedIndividual(this);

        setMyGridInfo(destinationGridInfo);
        getMyGridInfo().addIndividual(this);

        myDirections.clear();
        myDirections.addAll(getMyGridInfo().getPossibleOrderedDirections());
    }

    private void dropPheromone() {
        double maxPheromone = maxPheromone();
        if(hasFoodItem) {
            getMyGridInfo().putNumberAttributes(FOODPHEROMONE,
                    Math.max(maxPheromone-2, getMyGridInfo().getNumberAttribute(FOODPHEROMONE)));
            System.out.println(Math.max(maxPheromone-2, getMyGridInfo().getNumberAttribute(FOODPHEROMONE)));
        } else {
            getMyGridInfo().putNumberAttributes(HOMEPHEROMONE,
                    Math.max(maxPheromone-2, getMyGridInfo().getNumberAttribute(HOMEPHEROMONE)));
            System.out.println(Math.max(maxPheromone-2, getMyGridInfo().getNumberAttribute(HOMEPHEROMONE)));
        }
    }

    private List<Point> getInitialThree() {
        return getNeighborDirections(curDirection-1, curDirection+2);
    }

    private List<Point> getRestOfDirections() {
        return getNeighborDirections(curDirection+2, curDirection + myDirections.size()-1);
    }

    private List<Point> getAllNeighbors() {
        return getNeighborDirections(0, myDirections.size());
    }

    private List<Point> getNeighborDirections(int startIndex, int lastIndex) {
        List<Point> list = new ArrayList<>();
        for(int i=startIndex; i<lastIndex; i++) {
            list.add(myDirections.get((i + myDirections.size()) % myDirections.size()));
        }
        return list;
    }

    private void dropFood() {
        if(getMyGridInfo().getBooleanAttribute(GridAttribute.ISFOOD)) {
            hasFoodItem = true;
        } else if(getMyGridInfo().getBooleanAttribute(GridAttribute.ISHOME)) {
            hasFoodItem = false;
        }
    }

    private List<Point> possibleDirectionsToMove(List<Point> directions) {
        List<Point> list = new ArrayList<>();
        for(Point direction : directions) {
            GridInfo gridInfo = getMyGridInfo().getOneNeighborGrid(direction);
            //cannot move if packed or obstacle
            if(!(gridInfo.getBooleanAttribute(GridAttribute.ISPACKED) || gridInfo.getBooleanAttribute(GridAttribute.ISOBSTACLE))) {
                list.add(direction);
            }
        }
        return list;
    }

    private Point destinationDirection(List<Point> directions) {
        double[] possibilities = new double[directions.size()];
        for(int i=0; i<possibilities.length; i++) {
            double pheromone = getPheromoneOfNeighbor(directions.get(i));
            possibilities[i] = Math.pow(K + pheromone, N);
        }
        int index = pickOneRandomly(possibilities);
        return directions.get(index);
    }

    private double maxPheromone() {
        double max = 0;
        for(GridInfo neighborGridInfo : getMyGridInfo().getNeighborGrids()) {
            if(hasFoodItem) {
                max = Math.max(max, neighborGridInfo.getNumberAttribute(FOODPHEROMONE));
            } else {
                max = Math.max(max, neighborGridInfo.getNumberAttribute(HOMEPHEROMONE));
            }
        }
        System.out.println(max);
        return max;
    }

    private int pickOneRandomly(double[] possibilities) {
        double sum = 0;
        for(double temp : possibilities) {
            sum += temp;
        }
        double ran = Math.random() * sum;

        double temp = 0;
        for(int i=0; i<possibilities.length; i++) {
            temp += possibilities[i];
            if(ran <= temp) {
                return i;
            }
        }
        return possibilities.length-1;
    }

    private double getPheromoneOfNeighbor(Point direction) {
        if(hasFoodItem) {
            return getMyGridInfo().getOneNeighborGrid(direction).getNumberAttribute(HOMEPHEROMONE);
        }  else {
            return getMyGridInfo().getOneNeighborGrid(direction).getNumberAttribute(FOODPHEROMONE);
        }
    }
}
