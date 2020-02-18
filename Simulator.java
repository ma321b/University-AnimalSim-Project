import java.util.*;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing stegosauruss and tRexes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    public static void main(String[] args) {
        Simulator s = new Simulator();
        s.runLongSimulation();
    }
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a tRex will be created in any given grid position.
    private static final double tRex_CREATION_PROBABILITY = 0.04;
    // The probability that a stegosaurus will be created in any given grid position.
    private static final double stegosaurus_CREATION_PROBABILITY = 0.06;
    
    private static final double RAPTOR_CREATION_PROBABILITY = 0.05;
    
    private static final double TRICERATOPS_CREATION_PROBABILITY = 0.05;

    private static final double PLANT_CREATION_PROBABILITY = 0.1;

    // List of animals in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // A graphical view of the simulation.
    private SimulatorView view;
    // Simulation's time/date/day info.
    private TimeTrack time;
    // The weather in the simulation.
    private Weather weather;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        weather = new Weather();
        time = new TimeTrack();
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Stegosaurus.class, Color.ORANGE);
        view.setColor(TRex.class, Color.BLUE);
        view.setColor(Raptor.class, Color.CYAN);
        view.setColor(Triceratops.class, Color.MAGENTA);
        view.setColor(Plant.class, Color.GREEN);
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * tRex and stegosaurus.
     */
    public void simulateOneStep()
    {
        TimeTrack.incrementStep();
        Weather.changeWeather();
//        Disease.spreadPandemic();
        time.setCurrentTime();

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<>();
        // Let all Stegosauruses act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if (TimeTrack.getIsDay()) {
                // all animals act during the day
                actor.act(newActors);
            }
            else if (!TimeTrack.getIsDay()) {
                if (actor instanceof Prey) {
                    // only the preys move during the night
                    actor.act(newActors);
                }
            }
            if(!actor.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born tRexes and stegosauruss to the main lists.
        actors.addAll(newActors);

        view.showStatus(TimeTrack.getStep(), field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        // step = 0
        TimeTrack.setStep(0);
        time.setCurrentTime();

        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(TimeTrack.getStep(), field);
    }
    
    /**
     * Randomly populate the field with Actors.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= tRex_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    TRex TRex = new TRex(true, field, location);
                    actors.add(TRex);
                }
                else if(rand.nextDouble() <= stegosaurus_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Stegosaurus stegosaurus = new Stegosaurus(true, field, location);
                    actors.add(stegosaurus);
                }
                else if(rand.nextDouble() <= RAPTOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Raptor raptor = new Raptor(true, field, location);
                    actors.add(raptor);
                // else leave the location empty.
                }
                else if(rand.nextDouble() <= TRICERATOPS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Triceratops triceratops = new Triceratops(true, field, location);
                    actors.add(triceratops);
                // else leave the location empty.
                }
                else if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Plant plant = new Plant(field, location);
                    actors.add(plant);
                    // else leave the location empty.
                }
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
