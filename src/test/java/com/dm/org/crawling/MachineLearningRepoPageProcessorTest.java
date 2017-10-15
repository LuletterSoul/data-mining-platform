package com.dm.org.crawling;

import org.junit.Before;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

import static org.junit.Assert.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public class MachineLearningRepoPageProcessorTest {

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void process() throws Exception {
        Spider.create(new MachineLearningRepoPageProcessor())
                .addUrl("http://archive.ics.uci.edu/ml/datasets.html").thread(5).run();
    }

}