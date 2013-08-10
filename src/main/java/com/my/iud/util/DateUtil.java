package com.my.iud.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static int getDayOfWeek() {
        int index = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (index == 1) {
            index = 7;
        } else {
            index = index - 1;
        }
        return index;
    }

    public static Date getAfterDate(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, n);
        return c.getTime();
    }

    public static String getNextExecuteDay(int day[], int time[]) {
        boolean hasToday = false;
        int todayIndex = 0;
        int tempIndex = 0;
        int timeLength = time.length;
        String eDate = "";
        String eTime = "";

        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfTime = new SimpleDateFormat("HH");
        Date datetime = Calendar.getInstance().getTime();
        String dateStr = dfDate.format(datetime);
        String timeStr = dfTime.format(datetime);

        int curDayOfWeek = getDayOfWeek();
        for (int tday : day) {
            if (tday == curDayOfWeek) {
                eDate = dateStr;
                hasToday = true;
                todayIndex = tempIndex;
                break;
            }
            tempIndex++;
        }

        if (hasToday) {
            Arrays.sort(time);
            if (Integer.valueOf(timeStr) >= time[timeLength - 1]) {
                if (todayIndex == day.length - 1) {
                    eDate = dfDate.format(getAfterDate(day[0] + 7 - curDayOfWeek));
                } else {
                    eDate = dfDate.format(getAfterDate(day[todayIndex + 1] - curDayOfWeek));
                }

                if (Integer.valueOf(time[0]) < 10) {
                    eTime = "0" + String.valueOf(time[0]) + ":00:00";
                } else if (Integer.valueOf(time[0]) >= 10) {
                    eTime = String.valueOf(time[0]) + ":00:00";
                }

            } else {
                for (int ttime : time) {
                    if (Integer.valueOf(timeStr) + 1 <= Integer.valueOf(ttime)) {
                        if (Integer.valueOf(ttime) < 10) {
                            eTime = "0" + String.valueOf(ttime) + ":00:00";
                        } else if (Integer.valueOf(ttime) >= 10) {
                            eTime = String.valueOf(ttime) + ":00:00";
                        }
                        break;
                    }
                }
            }
        } else {
            Arrays.sort(day);
            Arrays.sort(time);
            int daylen = day.length;
            int maxDay = day[daylen - 1];
			if (maxDay > curDayOfWeek) {
                eDate = dfDate.format(getAfterDate(maxDay - curDayOfWeek));
				if (time[0] < 10) {
					eTime = "0" + String.valueOf(time[0]) + ":00:00";
				} else if (Integer.valueOf(time[0]) >= 10) {
					eTime = String.valueOf(time[0]) + ":00:00";
				}

			} else {
                eDate = dfDate.format(getAfterDate(day[0] + 7 - curDayOfWeek));
                if (time[0] < 10) {
                    eTime = "0" + time[0] + ":00:00";
                } else {
                    eTime = time[0] + ":00:00";
                }
            }
        }
        return eDate + " " + eTime;
    }
}
