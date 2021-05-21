import java.util.HashMap;
import java.util.Set;

/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A * для вычисления
 * путь по карте. Это состояние включает в себя набор «открытых путевых точек» и
 * еще один сборник «закрытых путевых точек». Кроме того, этот класс предоставляет
 * основные операции, необходимые алгоритму поиска пути A * для его выполнения
 * обработка.
 **/
public class AStarState
{
    /**
    *Массивы открытых и закрытых вершин
     **/
    HashMap<Integer,Waypoint> openCell =new HashMap<Integer,Waypoint>();
    HashMap<Integer,Waypoint> closeCell =new HashMap<Integer,Waypoint>();

    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     Эта функция должна проверить все вершины в наборе открытых вершин,
     и после этого она должна вернуть ссылку на вершину с наименьшей общей стоимостью.
     Если в "открытом" наборе нет вершин, функция возвращает NULL.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if(openCell.isEmpty())return null;
        double minCost=Integer.MAX_VALUE;
        Waypoint min=null;
       // Set<Integer> set=openCell.keySet();
        for(int key: openCell.keySet()){
            //System.out.println(openCell.keySet()+" = "+openCell.get(key).getTotalCost());
            if(openCell.get(key).getTotalCost()<minCost){
                minCost=openCell.get(key).getTotalCost();
                min=openCell.get(key);
            }
        }
        return min;
    }

    /**
     * - Если в наборе «открытых вершин» в настоящее время нет вершины для данного местоположения,
     *      то необходимо просто добавить новую вершину.
     * - Если в наборе «открытых вершин» уже есть вершина для этой локации,
     *      добавьте новую вершину только в том случае, если стоимость пути до новой вершины меньше
     *      стоимости пути до текущей. (Убедитесь, что используете не общую стоимость.)
     *      Другими словами, если путь через новую вершину короче, чем путь через текущую вершину,
     *      замените текущую вершину на новую
     * - Данный метод вернет значение true, если новая вершина была успешно добавлена в набор,
     *      и false в противном случае.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //double mincost;
        Waypoint min;
        int hash =newWP.loc.hashCode();//хэш код входного параметра
        for(int key: openCell.keySet()){
            if(hash==key){
                if(newWP.getPreviousCost()<openCell.get(key).getPreviousCost()){
                    openCell.put(hash,newWP);
                    return true;
                }
                return false;
            }
        }
        openCell.put(hash,newWP);
        return true;
    }


    /** Этот метод возвращает количество точек в наборе открытых вершин. **/
    public int numOpenWaypoints()
    {
        return openCell.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        int hash=loc.hashCode();
        closeCell.put(hash,openCell.get(hash));
        openCell.remove(hash);

    }

    /**
     Эта функция должна возвращать значение true,
     если указанное местоположение встречается в наборе закрытых вершин,
     и false в противном случае. Так как закрытые вершины хранятся в хэш-карте
     с расположениями в качестве ключевых значений, данный метод достаточно просто в реализации.
     **/
    public boolean isLocationClosed(Location loc)
    {
        int hash = loc.hashCode();
        for(int key:closeCell.keySet()){
            if(hash==key)return true;
        }
        return false;
    }
}
