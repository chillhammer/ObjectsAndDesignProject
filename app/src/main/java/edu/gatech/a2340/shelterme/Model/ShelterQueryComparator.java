package edu.gatech.a2340.shelterme.Model;

import java.util.Comparator;
import java.util.List;

import me.xdrop.fuzzywuzzy.model.ExtractedResult;

/**
 * Created by kpx on 3/6/2018.
 */

public class ShelterQueryComparator implements Comparator<Shelter> {

    private List<ExtractedResult> priority;

    /**
     * Constructor that sets the priority for the comparator
     * @param namePriority
     */
    public ShelterQueryComparator(List<ExtractedResult> namePriority) {
        priority = namePriority;
    }

    /**
     * Implementation of Comparator's compare method. Takes
     * priority and gives smaller values to matches earlier
     * in the list.
     * @param a the first Shelter to comapare
     * @param b the second Shelter to compare
     * @return the result of the comparison
     */
    @Override
    public int compare(Shelter a, Shelter b){
        for (ExtractedResult result : priority) {
            if (a.getName().equals(result.getString())) {
                return -1;
            }
            if (b.getName().equals(result.getString())) {
                return 1;
            }
        }
        return 0;
    }
}
