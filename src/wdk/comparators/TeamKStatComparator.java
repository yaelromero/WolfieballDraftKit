package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamKStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggKStat() > o2.getAggKStat()) {
            return -1;
        }
        else if(o2.getAggKStat() < o2.getAggKStat()) {
            return 1;
        }
        else
            return 0;
    }   
}
