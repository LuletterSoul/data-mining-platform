//package com.dm.org.utils;
//
//import com.vero.dm.model.DataSetAttribute;
//import com.vero.dm.model.DataSetCollection;
//import com.vero.dm.model.DataSetContainer;
//
//import java.util.Random;
//import java.util.UUID;
//
///**
// * @author 刘祥德 qq313700046@icloud.com .
// * @date created in  8:54 2017/7/6.
// * @description
// * @modified by:
// */
//public class TestDataGenerator
//{
//    private static DataSetCollection dataSetCollection;
//    private static DataSetContainer dataSetContainer;
//    private static DataSetAttribute dataSetAttribute;
//
//    public static String getRamdomUUID(int length)
//    {
//        return UUID.randomUUID().toString().substring(0, length);
//    }
//    public static String getRandomString(int length) { //length表示生成字符串的长度
//        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(base.length()-1);
//            sb.append(base.charAt(number));
//        }
//        return sb.toString();
//    }
//    public static DataSetCollection buildDataSetCollection()
//    {
//        dataSetCollection = new DataSetCollection();
//        //dataSetCollection.setCollectionId(UUID.randomUUID().toString());
//        dataSetCollection.setAbstractInfo("test collection"+ getRamdomUUID(4));
//        dataSetCollection.setArea("Computer");
//        dataSetCollection.setAssociatedTasks("Regression "+ getRamdomUUID(4));
//        dataSetCollection.setDescription("test description "+ getRamdomUUID(8));
//        dataSetCollection.setEnableMissing((byte) 1);
//        dataSetCollection.setRelevantPapers(("demo paper for test "+ getRamdomUUID(16)));
//        dataSetCollection.setTopics("test "+ getRamdomUUID(4));
//        return dataSetCollection;
//    }
//    public static DataSetContainer buildDataSetContainer()
//    {
//        dataSetContainer = new DataSetContainer();
//        Random random = new Random();
//        dataSetContainer.setData(getRandomString(4).getBytes());
//        dataSetContainer.setAttributeTypes(getRamdomUUID(8));
//        dataSetContainer.setFileType("text");
//        dataSetContainer.setSize(random.nextDouble());
//        dataSetContainer.setInstances(Long.MIN_VALUE);
//        dataSetContainer.setSetName("data set container " + getRandomString(4));
//        return dataSetContainer;
//    }
//    public static DataSetAttribute buildDataSetAttribute()
//    {
//        dataSetAttribute = new DataSetAttribute();
//        dataSetAttribute.setFieldName("SetAttribute+ " + getRandomString(4));
//        dataSetAttribute.setDescription(("set attribute + " + getRandomString(32)).getBytes());
//        return dataSetAttribute;
//    }
//}
