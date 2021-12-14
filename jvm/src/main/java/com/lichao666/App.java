package com.lichao666;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public static void main( String[] args ) throws UnknownHostException {

        System.out.println( "Hello World! \t" + a);
        System.out.println( "Hello World! \t" + b);


        System.out.println("localhost \t"+getLocalhostAddress());
        System.out.println("host \t"+getHostInetAddress("ICsn01"));

    }

    public static String getHostInetAddress(String host) throws UnknownHostException {
        InetAddress[] inetAddresses = InetAddress.getAllByName(host);
        String addrs = "";
        for (InetAddress inetAddress : inetAddresses) {
            addrs += inetAddress.getCanonicalHostName() + " ";
        }
        return addrs;
    }

    public static String getLocalhostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getCanonicalHostName();
    }

}
