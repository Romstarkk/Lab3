/**
 * Этот класс представляет собой один шаг в пути, созданном поиском пути A *
 * алгоритм. Путевые точки состоят из местоположения, предыдущая путевая точка в
 * path и некоторые значения стоимости, используемые для определения наилучшего пути.
 **/
public class Waypoint //представляет отдельные вершины в сгенерированном пути
{
    /** The location of this waypoint. **/
    Location loc;

    /**
     * Предыдущая путевая точка на этом пути или <code> null </code>, если это
     * корень поиска A *.
     **/
    Waypoint prevWaypoint;

    /**
     * В этом поле хранится общая предыдущая стоимость получения с начала
     * местоположение этой путевой точки через цепочку путевых точек. Это
     * фактическая стоимость следования по пути; он не включает никаких оценок.
     **/
    private float prevCost;

    /**
     * В этом поле хранится оценка оставшейся стоимости поездки из
     * этой путевой точки до конечного пункта назначения.
     **/
    private float remainingCost;


    /**
     * Создайте новую путевую точку для указанного места. Предыдущую путевую точка
     * можно указать необязательно, или ссылка может быть <code> null </code> для
     * указывают, что путевая точка является началом пути.
     **/
    public Waypoint(Location loc, Waypoint prevWaypoint)
    {
        this.loc = loc;
        this.prevWaypoint = prevWaypoint;
    }

    /** Returns the location of the waypoint. **/
    public Location getLocation()
    {
        return loc;
    }

    /**
     * Returns the previous waypoint in the path, or <code>null</code> if this
     * is the start of the path.
     **/
    public Waypoint getPrevious()
    {
        return prevWaypoint;
    }

    /**
     * Этот мутатор позволяет изменять как предыдущую, так и оставшуюся стоимость.
     * установить за один вызов метода. Обычно эти значения будут равны
     * в любом случае время.
     **/
    public void setCosts(float prevCost, float remainingCost)
    {
        this.prevCost = prevCost;
        this.remainingCost = remainingCost;
    }

    /**
     * Returns the actual cost of getting to this point from the starting
     * location, through the series of waypoints in this chain.
     **/
    public float getPreviousCost()
    {
        return prevCost;
    }

    /**
     * Returns an estimate of the remaining cost of traveling from this
     * point to the final destination.
     **/
    public float getRemainingCost()
    {
        return remainingCost;
    }

    /**
     * Returns the total cost estimate for this waypoint.  This includes the
     * actual cost of getting to this point from the starting location, plus
     * the estimate of the remaining cost of traveling from this point to
     * the final destination.
     **/
    public float getTotalCost()
    {
        return prevCost + remainingCost;
    }
}
