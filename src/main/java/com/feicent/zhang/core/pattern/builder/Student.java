package com.feicent.zhang.core.pattern.builder;

/**
 * http://www.cnblogs.com/yulinfeng/p/7282649.html
 */
//@ToString
public class Student {
	/*必填*/
	private String name;
    private int age;
    /*选填*/
    private String sex;
    private String grade;
    
    public static class Builder {
        private String name;
        private int age;
        private String sex = "";
        private String grade = "";

        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }
        public Builder grade(String grade) {
            this.grade = grade;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }
    
    private Student(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.sex = builder.sex;
        this.grade = builder.grade;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
    
}
