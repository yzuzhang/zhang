package com.feicent.zhang.util.serializer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class MainTest {
    
	public static void main(String[] args) throws IOException {
    	Person p = new Person(100001, "中华人民共和国");
        byte[] arr = ProtoStuffSerializerUtil.serialize(p);
        
        Person result = ProtoStuffSerializerUtil.deserialize(arr, Person.class);
        assertEquals(p.getId(), result.getId());
        assertEquals(p.getName(), result.getName());
        
        File file = new File("D:/person.ser");
        FileUtils.writeByteArrayToFile(file, arr, false);
        byte[] arr2 = FileUtils.readFileToByteArray(file);
        Person person = ProtoStuffSerializerUtil.deserialize(arr2, Person.class);
        System.out.println(person);
	}
    
}

class Person {
    int id;
    String name;
    
    public Person(){
      super();  
    }
    
    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
