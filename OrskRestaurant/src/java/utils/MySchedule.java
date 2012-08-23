/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.*;

/**
 *
 * @author rogvold
 *
 *
 */
public class MySchedule {

    public static final String HOLIDAY_STRING = "x";
    public static final String NOCTIDIAL_STRING = "k";
    public static final String EXAMPLE = "/08:30-20:00/x/09:00-21:00/09:00-21:00/09:00-22:30/09:00-21:00/k/";
    /**
     * format is following: /hh:mm-hh:mm/x/o/hh:mm-hh:mm/
     */
    private String input;
//    private List<String> schedule;

    public MySchedule(String input) {
        this.input = input;
    }

    public List<String> getSchedule() {
        if (input.charAt(0) == '/') {
            input = input.substring(1);
        }
        System.out.println("input = " + input);
        String[] array = input.split("\\/");
        List<String> list = Arrays.asList(array);
        return list;
    }

    public List<String> getPrettySchedule() {
        List<String> inpList = getSchedule();
        List<String> list = new ArrayList();
        Map<Integer, String> days = new HashMap();
        days.put(1, "пн ");
        days.put(2, "вт ");
        days.put(3, "ср ");
        days.put(4, "чт ");
        days.put(5, "пт ");
        days.put(6, "сб ");
        days.put(7, "вс ");
        int i = 0;
        for (String s : inpList) {
            i++;
            String a = days.get(i);
            if (s.equals(MySchedule.HOLIDAY_STRING)) {
                list.add(a + "выходной");
                continue;
            }
            if (s.equals(MySchedule.NOCTIDIAL_STRING)) {
                list.add(a + "круглосуточно");
                continue;
            }
            list.add(a + s);
        }
        return list;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
