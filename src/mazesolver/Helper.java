package mazesolver;
import java.awt.Point;
import java.util.*;

/**
 *
 * @author Atul Gupta
 */
public class Helper
{
    static int count = 0;
    static List<String> giveWays(int[][] maze ,String p , Point s , Point d,List<Point> travelled)
    {
        List<String> list = new ArrayList<>();
        if(s.x == d.x && s.y == d.y)
        {
            count++;
            list.add(p);
            return list;
        }
        Point right = new Point(s.x+1,s.y);
        Point down = new Point(s.x,s.y+1);
        Point left = new Point(s.x-1,s.y);
        Point up = new Point(s.x,s.y-1);
        if(right.x < maze[0].length && maze[right.y][right.x] != 1 && !travelled.contains(right))
        {
            List<Point> travelledNew = new ArrayList<>();
            travelledNew.addAll(travelled);
            travelledNew.add(right);
            list.addAll(giveWays(maze ,p+'R', right, d,travelledNew));
        }
        if(down.y < maze.length && maze[down.y][down.x] != 1 && !travelled.contains(down))
        {
            List<Point> travelledNew = new ArrayList<>();
            travelledNew.addAll(travelled);
            travelledNew.add(down);
            list.addAll(giveWays(maze ,p+'D',down, d,travelledNew));
        }
        if(left.x >= 0 && maze[left.y][left.x] != 1 && !travelled.contains(left))
        {
            List<Point> travelledNew = new ArrayList<>();
            travelledNew.addAll(travelled);
            travelledNew.add(left);
            list.addAll(giveWays(maze ,p+'L',left, d,travelledNew));
        }
        if(up.y >= 0 && maze[up.y][up.x] != 1 && !travelled.contains(up))
        {
            List<Point> travelledNew = new ArrayList<>();
            travelledNew.addAll(travelled);
            travelledNew.add(up);
            list.addAll(giveWays(maze ,p+'U',up, d,travelledNew));
        }
        return list;
    }
}
