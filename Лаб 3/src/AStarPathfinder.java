import java.util.HashMap;
import java.util.HashSet;


/**
 * Этот класс содержит реализацию алгоритма поиска пути A *. В
 * алгоритм реализован как статический метод, поскольку алгоритм поиска пути
 * действительно не нужно поддерживать какое-либо состояние между вызовами
 * алгоритм.
 */
public class AStarPathfinder
{
    /**
     * Эта константа содержит максимальный предел отсечения для стоимости путей. Если
     * конкретная путевая точка превышает этот предел стоимости, путевая точка
     * исключено.
     **/
    public static final float COST_LIMIT = 1e6f;


    /**
     * Попытки вычислить путь, который проходит между началом и концом
     * локации указанной карты. Если путь может быть найден, путевая точка
     * возвращается <em> последний </em> шаг пути; эта путевая точка может быть
     * раньше ходил назад к исходной точке. Если путь не может быть найден,
     * Возвращается <code> null </code>.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Variables necessary for the A* search.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Set up a starting waypoint to kick off the A* search.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;

        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Find the "best" (i.e. lowest-cost) waypoint so far.
            Waypoint best = state.getMinOpenWaypoint();

            // If the best location is the finish location then we're done!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }

            // Add/update all neighbors of the current best location.  This is
            // equivalent to trying all "next steps" from this location.
            takeNextStep(best, state);

            // Finally, move this location from the "open" list to the "closed"
            // list.
            state.closeWaypoint(best.getLocation());
        }

        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод принимает путевую точку и генерирует все действительные "следующие".
     * шаги "от этой путевой точки. Новые путевые точки добавляются в" открытую "
     * waypoints »коллекция переданного объекта состояния A *.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();

        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);

                // If "next location" is outside the map, skip it.
                if (!map.contains(nextLoc))
                    continue;

                // If "next location" is this location, skip it.
                if (nextLoc == loc)
                    continue;

                // If this location happens to already be in the "closed" set
                // then continue on with the next location.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Make a waypoint for this "next location."

                Waypoint nextWP = new Waypoint(nextLoc, currWP);

                // OK, we cheat and use the cost estimate to compute the actual
                // cost from the previous cell.  Then, we add in the cost from
                // the map cell we step onto, to incorporate barriers etc.

                float prevCost = currWP.getPreviousCost() +
                        estimateTravelCost(currWP.getLocation(),
                                nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);

                // Skip this "next location" if it is too costly.
                if (prevCost >= COST_LIMIT)
                    continue;

                nextWP.setCosts(prevCost,
                        estimateTravelCost(nextLoc, map.getFinish()));

                // Add the waypoint to the set of open waypoints.  If there
                // happens to already be a waypoint for this location, the new
                // waypoint only replaces the old waypoint if it is less costly
                // than the old one.
                state.addOpenWaypoint(nextWP);
            }
        }
    }

    /**
     * Оценивает стоимость поездки между двумя указанными местами.
     * Фактическая стоимость рассчитывается как расстояние по прямой между
     * два места.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}