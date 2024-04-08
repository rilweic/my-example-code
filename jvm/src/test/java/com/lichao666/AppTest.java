package com.lichao666;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Objects;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testEqual(){
        System.out.println("小学博士".equals("⼩学博⼠"));
    }

    @Test
    public void testEqual2(){
        System.out.println(Objects.equals("小学博士","⼩学博⼠"));
    }


}
