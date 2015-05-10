package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamSVStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggSVStat() > o2.getAggSVStat()) {
            return -1;
        }
        else if(o2.getAggSVStat() < o2.getAggSVStat()) {
            return 1;
        }
        else
            return 0;
    }   
}
