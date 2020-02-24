import java.util.List;
import java.util.Random;

/**
 * A simple model of a Mammoth.
 * Mammoths age, move, eat preys, and die.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */

public class Mammoth extends Prey
{
    // Characteristics shared by all mammoths (class variables).

    // The age at which a mammoth can start to breed.
    private static final int BREEDING_AGE = 2;
    private static final int MAX_AGE = 80;
    // The likelihood of a mammoth breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 7;
    // The food value of a single stegosaurus. In effect, this is the
    // number of steps a mammoth can go before it has to eat again.
    private static final int PLANT_FOOD_VALUE = 10;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The mammoth's age.
    private int age;
    // The mammoth's food level, which is increased by eating stegosaurus.
    private int foodLevel;

    private static final int MAMMOTH_MAX_APETITE = 20;

    /**
     * Create a mammoth. A mammoth can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the mammoth will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mammoth(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAMMOTH_MAX_APETITE);
        }
        else {
            age = 0;
            foodLevel = PLANT_FOOD_VALUE;
        }
    }

    /**
     * This is what the mammoth does most of the time: it eats plants.
     * In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newMammoths A list to return newly born mammoths.
     */
    public void act(List<Actor> newMammoths)
    {
        incrementAge();
        incrementHunger();
        super.act(newMammoths);
    }

    /**
     * Increase the age. This could result in the mammoth's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this mammoth more hungry. This could result in the mammoth's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this mammoth is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newMammoths A list to return newly born Mammoths.
     */
    public void giveBirth(List<Actor> newMammoths)
    {
        // New Mammoths are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Mammoth young = new Mammoth(false, field, loc);
            newMammoths.add(young);
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
     * A Mammoth can breed if it has reached the breeding age.
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
            if (age >= BREEDING_AGE && animal instanceof Mammoth) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill the mammoth's food level to a certain
     * maximum level.
     */
    public void satisfyHunger()
    {
        if ((foodLevel += PLANT_FOOD_VALUE) > MAMMOTH_MAX_APETITE){
            foodLevel = MAMMOTH_MAX_APETITE;
        }
        else {
            foodLevel += PLANT_FOOD_VALUE;
        }
    }

    /**
     * @return The maximum food level of the mammoth.
     */
    public int getMaxApetite()
    {
        return MAMMOTH_MAX_APETITE;
    }

    /**
     * @return The current food level of the mammoth.
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }
}
