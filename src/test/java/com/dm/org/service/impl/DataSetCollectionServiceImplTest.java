//package com.dm.org.service.impl;
//
//
//import com.dm.org.base.BaseServiceInitializer;
//import com.dm.org.model.DataSetCollection;
//import com.dm.org.model.DataSetContainer;
//import com.dm.org.utils.TestDataGenerator;
//import com.mchange.v1.util.ArrayUtils;
//import org.hibernate.Metamodel;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.Column;
//import org.hibernate.mapping.PersistentClass;
//import org.hibernate.metadata.ClassMetadata;
//import org.hibernate.metamodel.internal.MetamodelImpl;
//import org.hibernate.persister.entity.AbstractEntityPersister;
//import org.hibernate.persister.entity.EntityPersister;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.*;
//
//
///**
// * DataSetCollectionServiceImpl Tester.
// *
// * @author XiangDe Liu qq313700046@icloud.com
// * @version 1.0
// * @since
// *
// *        <pre>
// * July 6, 2017
// *        </pre>
// */
//public class DataSetCollectionServiceImplTest extends BaseServiceInitializer
//{
//
//    private DataSetCollectionServiceImpl dataSetCollectionService;
//
//    private DataSetCollection dataSetCollection;
//
//    private SessionFactory sessionFactory;
//
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//
//    @Autowired
//    public void setDataSetCollectionService(DataSetCollectionServiceImpl dataSetCollectionService)
//    {
//        this.dataSetCollectionService = dataSetCollectionService;
//    }
//
//    @Before
//
//    public void before()
//        throws Exception
//    {}
//
//    @After
//    public void after()
//        throws Exception
//    {
//
//    }
//
//    /**
//     * Method: save()
//     */
//    @Test
//    public void testSave()
//        throws Exception
//    {
//        dataSetCollection = TestDataGenerator.buildDataSetCollection();
//        DataSetContainer dataSetContainer = TestDataGenerator.buildDataSetContainer();
//        Set<DataSetContainer> containerSet = new HashSet<DataSetContainer>();
//        containerSet.add(dataSetContainer);
//        dataSetCollection.setDataSets(containerSet);
//        dataSetCollectionService.save(dataSetCollection);
//    }
//
//    /**
//     * Method: testMetaSoucesFetch()
//     */
//    @Test
//    public void testMetaSourcesFetch()
//                throws Exception
//    {
//        Metamodel metaModel = sessionFactory.getMetamodel();
//        MetamodelImpl metamodelImpl;
//        if(metaModel instanceof MetamodelImpl)
//        {
//            metamodelImpl = (MetamodelImpl) metaModel;
//            AbstractEntityPersister abstractEntityPersister =(AbstractEntityPersister)
//                    metamodelImpl.entityPersister(DataSetCollection.class);
//            String[] propertyNames= abstractEntityPersister.getPropertyNames();
//            String[] identifierCols=abstractEntityPersister.getIdentifierColumnNames();
//            String identifierPro = abstractEntityPersister.getIdentifierPropertyName();
//            for (String property :
//                    propertyNames)
//            {
//                String[] cols = abstractEntityPersister.getPropertyColumnNames(property);
//                for (String col :
//                        cols)
//                {
//                    System.out.println(property+":"+col);
//                }
//            }
//
//            for (String col :
//                    identifierCols) {
//                System.out.println(col);
//            }
//            System.out.println(identifierPro);
//        }
//    }
//
//    /**
//     * Method: findByProperty()
//     */
//    @Test
//    public void testFindByProperty()
//        throws Exception
//    {
//        List<DataSetCollection> list =dataSetCollectionService.findByProperty(null, "collectionId",
//            "402881e45d171357015d1713d09a0000");
//        dataSetCollection = list.get(0);
//        System.out.println(dataSetCollection);
//    }
//
//    /**
//     * Method: findAll()
//     */
//    @Test
//    public void testFindAll()
//        throws Exception
//    {
//        System.out.println(dataSetCollectionService.findAll());
//    }
//
//    /**
//     * Method: findAllByColumns()
//     */
//    @Test
//    public void testFindAllColumns()
//                throws Exception
//    {
//        String columnNames[] =new String[]{"collectionId", "abstractInfo","area","associatedTasks"};
//        List<Object[]> list = dataSetCollectionService.findAll(columnNames);
//        for (Object[] objects : list
//                )
//        {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < columnNames.length; i++)
//            {
//                stringBuilder.append(columnNames[i]).append(" : ").append(objects[i]).append(";");
//            }
//            System.out.println(stringBuilder.toString());
//            System.out.println("Entry Size:" + list.size());
//        }
//    }
//
//    /**
//     * Method: delete()
//     */
//    @Test
//    public void testDelete()
//                throws Exception
//    {
//        dataSetCollectionService
//                .deleteByProperty("collectionId","402881e45d1707d3015d1707db970000");
//    }
//
//    /**
//     * Method: deleteByProperties()
//     */
//    @Test
//    public void testDeleteByProperties()
//                throws Exception
//    {
//        Map<String, Object> constraintMap = new HashMap<String, Object>();
//        constraintMap.put("associatedTasks", "Regression!!!!");
//        constraintMap.put("enableMissing", 0);
//        dataSetCollectionService.deleteByProperties(constraintMap);
//    }
//    /**
//     * Method: saveBatch()
//     */
//    @Test
//    public void testSaveBatch()
//                throws Exception
//    {
//        Set<DataSetCollection> collections = new HashSet<DataSetCollection>();
//        for (int i = 0; i < 20; i++)
//        {
//            collections.add(TestDataGenerator.buildDataSetCollection());
//        }
//        dataSetCollectionService.saveBatch(collections);
//    }
//
//    /**
//     * Method: findByPropertyFuzzy()
//     */
//    @Test
//    public void testFindByPropertyFuzzy()
//                throws Exception
//    {
//        List<DataSetCollection> list = dataSetCollectionService
//                .findByPropertyFuzzy("associatedTasks", "Regression");
//        System.out.println(list);
//    }
//
//
//    /**
//     * Method: updateEntityBatch()
//     */
//    @Test
//    public void testUpdateEntityBatch()
//                throws Exception
//    {
//        updateMapping.put("description", "test description!!!!!");
//        updateMapping.put("associatedTasks", "Regression!!!!!");
//        conditonMapping.put("area", "Computer");
//        dataSetCollectionService.updateEntityBatch(updateMapping, conditonMapping);
//    }
//
//    /**
//     * Method: testByteArray()
//     */
//    @Test
//    public void testTestUpdateEntityBatch()
//                throws Exception
//    {
//        byte[] bytes = new byte[16];
//        for (int i = 0; i < 16; i++) {
//            bytes[i] = (byte) i;
//        }
//
//        for (byte b:
//        bytes){
//            System.out.println(Integer.toHexString(b));
//    }
//    }
//    /**
//     * Method: addDataSetContainer()
//     */
//    @Test
//    public void testAddDataSetContainer()
//                throws Exception
//    {
//        DataSetContainer dataSetContainer = TestDataGenerator.buildDataSetContainer();
//        dataSetCollectionService.addDataSetContainer("402881e45d252234015d25223e310000",
//                dataSetContainer);
//    }
//
//    /**
//     * Method: removeDataSetContainer()
//     */
//    @Test
//    public void testRemoveDataSetContainer()
//                throws Exception
//    {
//        dataSetCollectionService.removeDataSetContainer("402881e45d252234015d25223e310000",
//                "402881e45d255885015d255890a80000");
//    }
//
//
//}
