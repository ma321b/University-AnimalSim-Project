import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.HashSet;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // animal's gender.
    private boolean male;
    // Whether the animal is diseased
    private boolean diseased;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        Random rand = new Random();
        diseased = false;
        male = rand.nextBoolean();
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newActors A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newActors);

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Set the animal as diseased
     */
    public void setDiseased()
    {
        diseased = true;
    }

    /**
     * Set the animal as healthy (undiseased)
     */
    public void setHealthy()
    {
        diseased = false;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    /**
     * @return True if the animal is Male. Else return false.
     */
    protected boolean isMale()
    {
        return male;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * @return True if the animal is diseased. Else return false.
     */
    public boolean isDiseased()
    {
        return diseased;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    abstract public boolean mate();
}

