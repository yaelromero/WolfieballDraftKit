package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamRStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggRStat() > o2.getAggRStat()) {
            return -1;
        }
        else if(o2.getAggRStat() < o2.getAggRStat()) {
            return 1;
        }
        else
            return 0;
    }   
}
