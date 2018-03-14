package com.feicent.zhang.util.serializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.feicent.zhang.entity.Simple;

/**
 * https://www.cnblogs.com/520playboy/p/6341490.html
 * @date 2018年1月15日
 */
public class KyroSerializable {  

	public static final String fileName = "D:/kyro.data";
	
    public static void main(String[] args) throws IOException {  
        long start =  System.currentTimeMillis();  
        setSerializableObject();
        System.out.println("Kryo 序列化时间:" + (System.currentTimeMillis() - start) + " ms" );  
        
        start =  System.currentTimeMillis();  
        getSerializableObject();
        System.out.println("Kryo 反序列化时间:" + (System.currentTimeMillis() - start) + " ms");  
  
    }  
  
    public static void setSerializableObject() throws FileNotFoundException{  
        Kryo kryo = new Kryo();  
        kryo.setReferences(false);  
        kryo.setRegistrationRequired(false);  
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());  
        kryo.register(Simple.class);

        Output output = new Output(new FileOutputStream(fileName));  
        for (int i = 0; i < 500000; i++) {  
            Map<String,Integer> map = new HashMap<String, Integer>(2);  
            map.put("zhang0", i);  
            map.put("zhang1", i);  
            kryo.writeObject(output, new Simple("zhang"+i,(i+1),map));  
        }  
        output.flush();  
        output.close();  
    }  
  
  
    public static void getSerializableObject(){  
        Kryo kryo = new Kryo();  
        kryo.setReferences(false);  
        kryo.setRegistrationRequired(false);  
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());  
        Input input;  
        try {  
            input = new Input(new FileInputStream(fileName));  
            @SuppressWarnings("unused")
			Simple simple =null;  
            while((simple=kryo.readObject(input, Simple.class)) != null){  
                //System.out.println(simple.getAge() + "  " + simple.getName() + "  " + simple.getMap().toString());  
            }  
  
            input.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch(KryoException e){  
  
        }  
    }  
  
}
