import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * This is the class Raptor which is a Predator in the
 * simulation.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Raptor extends Predator
{
    // Characteristics shared by all Raptors (class variables).

    // The age at which a Raptor can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a Raptor can live.
    private static final int MAX_AGE = 160;
    // The likelihood of a Raptor breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The food value of a single prey. In effect, this is the
    // number of steps a Raptor can go before it has to eat again.
    private static final int PREY_FOOD_VALUE = 10;
    // A shared random number generator to control breeding.
    private static final int RAPTOR_MAX_APETITE = 40;
    
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The Raptor's age.
    private int age;
    // The Raptor's food level, which is increased by eating preys.
    private int foodLevel;

    /**
     * Create a Raptor. A Raptor can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the Raptor will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Raptor(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RAPTOR_MAX_APETITE);
        }
        else {
            age = 0;
            foodLevel = PREY_FOOD_VALUE;
        }
    }

    /**
     * This is what the Raptor does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newRaptors A list to return newly born Raptors.
     */
    public void act(List<Actor> newRaptors)
    {
        incrementAge();
        incrementHunger();
        super.act(newRaptors);
    }

    /**
     * Increase the age. This could result in the Raptor's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this Raptor more hungry. This could result in the Raptor's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this Raptor is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRaptors A list to return newly born Raptors.
     */
    public void giveBirth(List<Actor> newRaptors)
    {
        // New Raptors are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Raptor young = new Raptor(false, field, loc);
            newRaptors.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A Raptor can breed if it has reached the breeding age
     * and is able to mate (via the mate() method).
     * @return True if the raptor can breed. Otherwise return false.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && mate();
    }

    /**
     * @return True if two animals of the same species are in adjacent
     * fields and are of an opposite gender. False otherwise.
     */
    public boolean mate()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (age >= BREEDING_AGE && animal instanceof Raptor) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill the animal's food level to a certain
     * maximum level.
     */
    public void satisfyHunger()
    {
        if ((foodLevel += PREY_FOOD_VALUE) > RAPTOR_MAX_APETITE) {
            foodLevel = RAPTOR_MAX_APETITE;
        }
        else {
            foodLevel += PREY_FOOD_VALUE;
        }
    }

    /**
     * @return The maximum food level of the raptor.
     */
    public int getMaxApetite()
    {
        return RAPTOR_MAX_APETITE;
    }

    /**
     * @return The current food level of the animal.
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }
}