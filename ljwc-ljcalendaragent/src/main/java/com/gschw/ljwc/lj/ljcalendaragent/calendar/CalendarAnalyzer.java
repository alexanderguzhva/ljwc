package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collection;

import java.util.regex.Pattern;


/**
 * Created by nop on 8/12/15.
 */
public class CalendarAnalyzer {

    private static Logger logger = LoggerFactory.getLogger(CalendarAnalyzer.class);

    private LinksExtractor extractor;

    public CalendarAnalyzer(LinksExtractor extractor) {
        this.extractor = extractor;
    }

    //
    public void getYearsFromCalendar(String baseURL, Collection<String> yearsURLs)
    {
        ////
        String calendarURL = String.format("%s/calendar", baseURL);

        //// find all links that match years, (http://crustgroup.livejournal.com/2009/)
        Set<String> years = new TreeSet<>();
        Pattern yearsCalendarPattern = Pattern.compile(String.format("%s/\\d{4}/", baseURL));
        extractor.ExtractLinks(calendarURL, yearsCalendarPattern, years);

        ////
        yearsURLs.addAll(years);
        //years.add(String.format("%s/2014/", baseURL));
    }

    //
    public void getMonthsFromYears(Collection<String> yearsURLs, Collection<String> monthsURLs)
    {
        //// ok, now visit all these pages and grab month information (http://crustgroup.livejournal.com/2009/04/)
        Set<String> monthsToCheck = new TreeSet<String>();
        for (String ie : yearsURLs)
        {
            Pattern monthsCalendarPatterns = Pattern.compile(String.format("%s\\d{2}/", ie));

            extractor.ExtractLinks(ie, monthsCalendarPatterns, monthsToCheck);
        }

        ////
        monthsURLs.addAll(monthsToCheck);
    }

    //
    public void getPostsFromMonths(Collection<String> monthURLs, Collection<String> postsURLs)
    {
        //// now, visit each page
        Set<String> posts = new TreeSet<String>();
        for (String ie : monthURLs)
        {
            String baseURL;
            try
            {
                URI uri = new URI(ie);
                baseURL = uri.getHost();
                //baseURL = String2URI.extractHost(ie).toString();
            }
            catch (Exception ex)
            {
                //// skip
                logger.warn(String.format("Could not extract host from {}", ie));
                continue;
            }

            ////
            Pattern singlePattern = Pattern.compile(String.format("%s/\\d+.html", baseURL));

            extractor.ExtractLinks(ie, singlePattern, posts);
        }

        ////
        postsURLs.addAll(posts);
    }

}
