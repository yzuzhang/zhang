package com.feicent.zhang.entity;

/**
 * @author: yzuzhang
 * @date: 2018/6/19 20:11
 * @desc:
 */
public class HashCode {
    private int id;
    private String name;
    private String gender;

    public HashCode() {
        super();
    }

    public HashCode(String name, String gender) {
        super();
        this.name = name;
        this.gender = gender;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;
        final HashCode other = (HashCode)o;
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) return false;
        if (this.gender == null ? other.gender != null : !this.gender.equals(other.gender)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + super.hashCode();
        result = result * PRIME + (this.name == null ? 0 : this.name.hashCode());
        result = result * PRIME + (this.gender == null ? 0 : this.gender.hashCode());
        return result;
    }

    public static void main(String[] args) {
        HashCode tomcat = new HashCode("tomcat", "男");
        HashCode spring = new HashCode("spring", "女");

        System.out.println(tomcat.hashCode());
        System.out.println(spring.hashCode());

        System.out.println(spring.equals(tomcat));
    }

}
