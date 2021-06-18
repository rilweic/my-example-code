package com.lichao666;

/**
 * Hello world!
 *
 */
public class App 
{
    private static int a = 1;
    static {
        a = 2;
        b = 10;
    }
    private static int b = 100;

    public static void main( String[] args )
    {

        System.out.println( "Hello World! \t" + a);
        System.out.println( "Hello World! \t" + b);
    }
}
