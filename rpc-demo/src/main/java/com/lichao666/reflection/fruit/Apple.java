package com.lichao666.reflection.fruit;

/**
 * @author lichao
 * @date 2021/1/15
 */
public class Apple extends Fruit{
    public Apple(String name,double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String comment() {
        return "苹果派";
    }

    public void makeApplePie(String maker){
        System.out.println(maker+"制作苹果派"+this.name+",价格："+this.price * 2.0);
    }
}
