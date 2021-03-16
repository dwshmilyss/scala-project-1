package com.yiban.hive.udf.java.dev;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 输入：
 * John Smith
 * John and Ann White
 * Ted Green
 * Dorothy
 *
 * 输出：
 * John	Smith
 * John	White
 * Ann White
 * Ted Green
 *
 * 需求：
 * 对输入数据进行清洗，输入数据中有的行包含多个名字，有的行只有姓或者只有名
 * 包含多个姓名的要拆分开
 * 只有姓或者名的要去掉
 *
 * CREATE EXTERNAL TABLE people (name string) STORED AS TEXTFILE LOCATION '/data/name';
 *
 * add jar hdfs://gagcluster/tmp/hiveUDF.jar;
 *
 * CREATE TEMPORARY FUNCTION process_names AS 'com.yiban.hive.udf.java.dev.NameParserGenericUDTF';
 */
public class NameParserGenericUDTF extends GenericUDTF {
    private PrimitiveObjectInspector stringOI = null;

    /**
     * 该方法中，我们将指定输入输出参数：输入参数的ObjectInspector与输出参数的StructObjectInspector
     * @param args
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {

        if (args.length != 1) {
            throw new UDFArgumentException("NameParserGenericUDTF() takes exactly one argument");
        }

        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                && ((PrimitiveObjectInspector) args[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("NameParserGenericUDTF() takes a string as a parameter");
        }

        // 输入格式（inspectors）
        stringOI = (PrimitiveObjectInspector) args[0];

        // 输出格式（inspectors） -- 有两个属性的对象
        List<String> fieldNames = new ArrayList<String>(2);
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>(2);
        fieldNames.add("name");
        fieldNames.add("surname");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    public ArrayList<Object[]> processInputRecord(String name){
        ArrayList<Object[]> result = new ArrayList<Object[]>();

        // 忽略null值与空值
        if (name == null || name.isEmpty()) {
            return result;
        }

        String[] tokens = name.split("\\s+");

        if (tokens.length == 2){
            result.add(new Object[] { tokens[0], tokens[1] });
        }else if (tokens.length == 4 && tokens[1].equals("and")){
            result.add(new Object[] { tokens[0], tokens[3] });
            result.add(new Object[] { tokens[2], tokens[3] });
        }

        return result;
    }

    /**
     * 我们将处理一条输入记录，输出若干条结果记录
     * @param record
     * @throws HiveException
     */
    @Override
    public void process(Object[] record) throws HiveException {

        final String name = stringOI.getPrimitiveJavaObject(record[0]).toString();

        ArrayList<Object[]> results = processInputRecord(name);

        Iterator<Object[]> it = results.iterator();

        while (it.hasNext()){
            Object[] r = it.next();
            forward(r);
        }
    }

    /**
     * 当没有记录处理的时候该方法会被调用，用来清理代码或者产生额外的输出
     * @throws HiveException
     */
    @Override
    public void close() throws HiveException {
        // do nothing
    }
}
