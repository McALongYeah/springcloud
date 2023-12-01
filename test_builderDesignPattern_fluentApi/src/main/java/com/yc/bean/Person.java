package com.yc.bean;

public class Person {
    private String name;
    private Integer age;
    private String gender;
    public Person(Builder b){
        this.name = b.name;
        this.age = b.age;
        this.gender = b.gender;
    }

    public static final class Builder{
        private String name;        
        private Integer age;
        private String gender;

//          以实体属性名为方法名,为属性赋值,并返回this builder
        public Builder name(String name) {
            this.name = name;
            return this; //this指builder对象
        }
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }
        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }
        //提供一个builder方法,使用builder收集属性创建实体类
        //实体类的创建方式多种多样,只要达到目的即可,通常提供全属性的构造器,或者以builder为参数的构造器
        public static Builder builder(){
            return new Builder();
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }
}
