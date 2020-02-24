
/**
 * A subclass of weather for when it's rainy weather.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Rain extends Weather
{
    // current weather name
    String weatherName;

    public Rain()
    {
        weatherName = "Rain";
    }

    /**
     * @return The current weather name (i.e., rain).
     */
    public String getWeatherName()
    {
        return weatherName;
    }
}
