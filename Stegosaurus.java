import java.util.List;
import java.util.Random;

/**
 * A simple model of a stegosaurus.
 * Stegosauruses age, move, breed, and die.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Stegosaurus extends Prey
{
    // Characteristics shared by all stegosaurus (class variables).

    // The age at which a stegosaurus can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a stegosaurus can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a stegosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    private static final int STEGOSAURUS_MAX_APETITE = 10;

    private static final int PLANT_FOOD_VALUE = 10;

    // Individual characteristics (instance fields).

    // The stegosaurus's age.
    private int age;

    private int foodLevel;

    /**
     * Create a new stegosaurus. A stegosaurus may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the stegosaurus will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Stegosaurus(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        //age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(STEGOSAURUS_MAX_APETITE);
        }
        else {
            age = 0;
            foodLevel = PLANT_FOOD_VALUE;
        }
        }

    /**
     * This is what the stegosaurus does most of the time - it runs
     * around and eats plants. Sometimes it will breed or die of old age.
     * @param newStegosaurus A list to return newly born stegosaurus.
     */
    public void act(List<Actor> newStegosaurus)
    {
        incrementAge();
        incrementHunger();
        super.act(newStegosaurus);
    }

    /**
     * Increase the age.
     * This could result in the stegosaurus's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this stegosaurus more hungry. This could result in the its death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * @return The current food level of the stegosaurus.
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }

    /**
     * @return The maximum food level of the stegosaurus.
     */
    public int getMaxApetite()
    {
        return STEGOSAURUS_MAX_APETITE;
    }

    /**
     * Check whether or not this stegosaurus is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newStegosaurus A list to return newly born stegosauruss.
     */
    public void giveBirth(List<Actor> newStegosaurus)
    {
        // New stegosaurus are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Stegosaurus young = new Stegosaurus(false, field, loc);
            newStegosaurus.add(young);
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
     * A Stegosaurus can breed if it has reached the breeding age.
     * @return true if the Stegosaurus can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && mate();
    }

    /**
     * @return True if two same species are in adjacent fields and are
     *         of an opposite gender. Return False otherwise.
     */
    public boolean mate()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (age >= BREEDING_AGE && animal instanceof Stegosaurus) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill the stegosaurus's food level to a certain
     * maximum level.
     */
    public void satisfyHunger()
    {
        if ((foodLevel += PLANT_FOOD_VALUE) > STEGOSAURUS_MAX_APETITE){
            foodLevel = STEGOSAURUS_MAX_APETITE;
        }
        else {
            foodLevel += PLANT_FOOD_VALUE;
        }
    }
}