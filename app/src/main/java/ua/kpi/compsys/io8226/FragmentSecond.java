package ua.kpi.compsys.io8226;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FragmentSecond extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // -------------------------- PART 1 ----------------------------------


        // Task 1

        Contents contents = new Contents();
        System.out.println("Завдання 1");
        System.out.println("------------------------");
        HashMap<String, ArrayList<String>> groupedStudents =
                contents.groupStudents(Contents.studentStr);
        System.out.println();

        // Task 2

        System.out.println("Завдання 2");
        System.out.println("------------------------");
        int[] points = new int[] {12, 12, 12, 12, 12, 12, 12, 16};
        HashMap<String, HashMap<String, ArrayList<Integer>>> grades =
                contents.fillGrades(groupedStudents, points);
        System.out.println();

        // Task 3

        System.out.println("Завдання 3");
        System.out.println("------------------------");
        HashMap<String, HashMap<String, Integer>> gradesSum = contents.showGradesSum(grades);
        System.out.println();

        // Task 4

        System.out.println("Завдання 4");
        System.out.println("------------------------");
        HashMap<String, Float> averages = contents.showAvgGradesInGroups(gradesSum);
        System.out.println();

        // Task 5

        System.out.println("Завдання 5");
        System.out.println("------------------------");
        HashMap<String, ArrayList<String>> bests = contents.showBestInGroups(gradesSum);
        System.out.println();




        // -------------------------- PART 2 ----------------------------------


        TimeOS time1 = new TimeOS(11,12,13);
        TimeOS time2 = new TimeOS();
        TimeOS time3 = new TimeOS(new Date());
        System.out.println(time1.toString());
        System.out.println(time2.toString());
        System.out.println(time3.toString());
        System.out.println(time1.addTime(time3));
        System.out.println(TimeOS.add2Times(time1, time3));
        System.out.println(time1.subtractTime(time3));
        System.out.println(TimeOS.subtract2Times(time1, time3));


        // Tests that has been added after corrections
        TimeOS midnight = new TimeOS(0,0,0);
        TimeOS middleOfTheDay = new TimeOS(12,0,0);
        System.out.println(TimeOS.subtract2Times(middleOfTheDay, middleOfTheDay));
        System.out.println(TimeOS.add2Times(middleOfTheDay, middleOfTheDay));
        System.out.println(TimeOS.subtract2Times(midnight, middleOfTheDay));
        System.out.println(TimeOS.add2Times(midnight, middleOfTheDay));


        // -------------------------------------------------------------------

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }
}