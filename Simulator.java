
import java.util.*;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing stegosauruss and tRexes.
 * 
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Simulator
{
    public static void main(String[] args) {
        Simulator s = new Simulator();
        s.runLongSimulation();
    }
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 140;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // The probability that a tRex will be created in any given grid position.
    private static final double TREX_CREATION_PROBABILITY = 0.04;
    // The probability that a stegosaurus will be created in any given grid position.
    private static final double STEGOSAURUS_CREATION_PROBABILITY = 0.07;

    private static final double RAPTOR_CREATION_PROBABILITY = 0.03;

    private static final double TRICERATOPS_CREATION_PROBABILITY = 0.05;

    private static final double PLANT_CREATION_PROBABILITY = 0.04;

    private static final double MAMMOTH_CREATION_PROBABILITY = 0.05;

    private int x = 0;

    // List of animals in the field.
    private static List<Actor> actors;
    private static List<Location> allLocations;
    // The current state of the field.
    private static Field field;
    // A graphical view of the simulation.
    private List<SimulatorView> views;
    // Simulation's time/date/day info.
    private TimeTrack time;
    // The weather in the simulation.
    private Weather currentWeather;

    private static boolean meteorStrike = false;
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
        Weather.addWeather(new Fog());
        Weather.addWeather(new Rain());
        Weather.addWeather(new Snow());
        Weather.setWeather(new Rain());

        time = new TimeTrack();
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        actors = new ArrayList<>();
        allLocations = new ArrayList<>();
        field = new Field(depth, width);
        views = new ArrayList<>();
        

        // Create a view of the state of each location in the field.
        
        
        SimulatorView view = new GridView(depth, width);
        view.setColor(Stegosaurus.class, Color.ORANGE.brighter());
        view.setColor(TRex.class, Color.BLUE);
        view.setColor(Raptor.class, Color.CYAN);
        view.setColor(Triceratops.class, Color.MAGENTA);
        view.setColor(Plant.class, Color.GREEN);
        view.setColor(Fire.class, Color.RED);
        view.setColor(Mammoth.class, Color.ORANGE.darker());
        
        views.add(view);
        
        view = new GraphView(500, 150, 500);
        view.setColor(Stegosaurus.class, Color.ORANGE.brighter());
        view.setColor(TRex.class, Color.BLUE);
        view.setColor(Raptor.class, Color.CYAN);
        view.setColor(Triceratops.class, Color.MAGENTA);
        view.setColor(Plant.class, Color.GREEN);
        view.setColor(Mammoth.class, Color.ORANGE.darker());
        views.add(view);
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
        for(int step = 1; step <= numSteps && views.get(0).isViable(field); step++) {
            simulateOneStep();
            if (Weather.getCurrentWeather() instanceof Snow) {
                // make the actors act slowly if the weather is snowy
                delay(30);
            }
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
        time.setCurrentTime();
        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<>();
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if (TimeTrack.getIsDay()) {
                // all animals act during the day
                actor.act(newActors);
            }
            else {
                if (actor instanceof Plant || actor instanceof Fire) {
                    actor.act(newActors);
                }
            }
            if(! actor.isActive()) {
                it.remove();
            }
        }

        if (TimeTrack.getStep() % 800 == 0){
            volcanoErupt();
        }
        // Add the newly born actors to the main lists.

        actors.addAll(newActors);
        updateViews();
    }
    
    /**
     * Update all existing views.
     */
    private void updateViews()
    {
        for (SimulatorView view : views) {
            view.showStatus(TimeTrack.getStep(), field);
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        time.reset();
        actors.clear();
        populate();
        updateViews();
    }

    /**
     * Randomly populate the field with tRexes and stegosauruss.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= TREX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    TRex TRex = new TRex(true, field, location);
                    actors.add(TRex);
                    allLocations.add(location);
                }
                else if(rand.nextDouble() <= STEGOSAURUS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Stegosaurus stegosaurus = new Stegosaurus(true, field, location);
                    actors.add(stegosaurus);
                    allLocations.add(location);
                }
                else if(rand.nextDouble() <= RAPTOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Raptor raptor = new Raptor(true, field, location);
                    actors.add(raptor);
                    allLocations.add(location);
                    // else leave the location empty.
                }
                else if(rand.nextDouble() <= TRICERATOPS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Triceratops triceratops = new Triceratops(true, field, location);
                    actors.add(triceratops);
                    allLocations.add(location);
                    // else leave the location empty.
                }
                else if (rand.nextDouble() <= MAMMOTH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Mammoth mammoth = new Mammoth(true, field, location);
                    actors.add(mammoth);
                    allLocations.add(location);
                    // else leave the location empty.
                }
                else if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Plant plant = new Plant(field, location);
                    actors.add(plant);
                    allLocations.add(location);
                    // else leave the location empty.
                }
            }
        }
    }


    public void volcanoErupt(){
        Location loc = new Location(0,0);
        Fire fire = new Fire(field, loc);
        actors.add(fire);
    }

    public static List<Location> getLocationList()
    {
        return allLocations;
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
