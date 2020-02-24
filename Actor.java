import java.util.List;
/**
 * Abstract class Actor which is a superclass to all elements
 * which act in some way to affect the simulation.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public abstract class Actor
{
    /**
     * (Abstract) Make the actors 'act' i.e., make them do
     * the usual stuff.
     * @param newActors A list containing the new Actor objects
     */
    abstract public void act(List<Actor> newActors);

    /**
     * (Abstract)
     * @return True if the Actor is alive. Else return false.
     */
    abstract boolean isActive();
}
