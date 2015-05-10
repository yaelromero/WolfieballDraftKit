package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamWHIPStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggWHIPStat() > o2.getAggWHIPStat()) {
            return -1;
        }
        else if(o2.getAggWHIPStat() < o2.getAggWHIPStat()) {
            return 1;
        }
        else
            return 0;
    }   
}
