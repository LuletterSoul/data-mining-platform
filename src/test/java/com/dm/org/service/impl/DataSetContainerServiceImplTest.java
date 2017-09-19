//package com.dm.org.service.impl;
//
//
//import com.dm.org.base.BaseServiceInitializer;
//import com.dm.org.model.DataSetContainer;
//import com.dm.org.utils.TestDataGenerator;
//import org.junit.Before;
//import org.junit.After;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import static org.junit.Assert.*;
//import java.util.List;
//
//
///**
// * DataSetContainerServiceImpl Tester.
// *
// * @author XiangDe Liu qq313700046@icloud.com
// * @since
// *
// *        <pre>
// * July 6, 2017
// *        </pre>
// *
// * @version 1.0
// */
//public class DataSetContainerServiceImplTest extends BaseServiceInitializer
//{
//    private DataSetContainerServiceImpl dataSetContainerService;
//
//    private DataSetContainer dataSetContainer;
//
//    @Autowired
//    public void setDataSetContainerService(DataSetContainerServiceImpl dataSetContainerService)
//    {
//        this.dataSetContainerService = dataSetContainerService;
//    }
//
//    @Before
//    public void before()
//        throws Exception
//    {
//        dataSetContainer = TestDataGenerator.buildDataSetContainer();
//    }
//
//    @After
//    public void after()
//        throws Exception
//    {
//
//    }
//
//    /**
//     * Method: deleteById()
//     */
//    @Test
//    public void testDeleteById()
//                throws Exception
//    {
//        dataSetContainerService.deleteById("402881e95d260ab2015d260abc8d0000");
//    }
//
//    /**
//     * Method: fetchDataSetContainers()
//     */
//    @Test
//    public void testFetchDataSetContainers()
//                throws Exception
//    {
//        List<DataSetContainer> containers = dataSetContainerService
//                .fetchDataSetContainers("402881e45d252234015d25223e310000");
//
//        for (DataSetContainer container :containers
//                )
//        {
//            System.out.println(container);
//        }
//    }
//    /**
//     * Method: findAll()
//     */
//    @Test
//    public void testFindAll()
//                throws Exception
//    {
//        List<DataSetContainer> containers = dataSetContainerService.findAll();
//        for (DataSetContainer container :
//                containers)
//        {
//            System.out.println(container);
//        }
//    }
//
//}
