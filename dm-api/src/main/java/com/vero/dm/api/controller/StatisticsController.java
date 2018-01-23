package com.vero.dm.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION+"/statistics")
public class StatisticsController
{
    @RequestMapping(value = "/systemStatistics", method = RequestMethod.GET)
    public String systemStatistics()
    {
        return "{\n" + "  \"code\": 20000,\n" + "  \"studentWeekTraffic\": {\n"
               + "    \"weekTimes\": 1,\n" + "    \"month\": \"September\",\n"
               + "    \"dayTrafficMap\": {\n" + "      \"Monday\": 157,\n"
               + "      \"Tuesday\": 97,\n" + "      \"Wednesday\": 185,\n"
               + "      \"Thursday\": 75,\n" + "      \"Friday\": 67,\n"
               + "      \"Saturday\": 95,\n" + "      \"Sunday\": 159\n" + "    }\n" + "  },\n"
               + "  \"teacherWeekTraffic\": {\n" + "    \"weekTimes\": 1,\n"
               + "    \"month\": \"September\",\n" + "    \"dayTrafficMap\": {\n"
               + "      \"Monday\": 81,\n" + "      \"Tuesday\": 62,\n"
               + "      \"Wednesday\": 78,\n" + "      \"Thursday\": 82,\n"
               + "      \"Friday\": 70,\n" + "      \"Saturday\": 70,\n" + "      \"Sunday\": 78\n"
               + "    }\n" + "  }\n" + "}";
    }
}
