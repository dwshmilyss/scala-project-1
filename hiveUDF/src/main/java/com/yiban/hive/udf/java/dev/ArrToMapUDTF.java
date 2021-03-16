package com.yiban.hive.udf.java.dev;

import com.google.common.collect.Lists;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.List;

public class ArrToMapUDTF extends GenericUDTF {
    private String[] obj = new String[3];

    /**
     * 返回类型为String,String
     *
     * @param argOIs
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //columnNameList里面的项和resultTypeList应该是一一对应的
        List<String> columnNameList = Lists.newLinkedList();
        columnNameList.add("ind");
        columnNameList.add("key");
        columnNameList.add("value");
        List<ObjectInspector> resultTypeList = Lists.newLinkedList();
        resultTypeList.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        resultTypeList.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        resultTypeList.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        //返回分别为列名和列类型
        return ObjectInspectorFactory.getStandardStructObjectInspector(columnNameList, resultTypeList);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        if (args[1] == null) {
            return;
        }
        obj[0] = args[0].toString();
        String arg1 = args[1].toString();
        String[] arr1 = arg1.split(",");
        String[] arr2 = null;
        if (args[2] != null) {
            arr2 = args[2].toString().split(",");
        }

        for (int i = 0; i < arr1.length; i++) {
            obj[1] = arr1[i];
            if (arr2 != null && arr2.length > i) {
                obj[2] = arr2[i];
            } else {
                obj[2] = null;
            }
            forward(obj);

        }
    }

    @Override
    public void close() throws HiveException {

    }
}
