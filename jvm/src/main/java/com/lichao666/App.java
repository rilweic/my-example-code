package com.lichao666;

import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

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

    public static void main2( String[] args ) throws UnknownHostException {



        System.out.println("localhost \t"+getLocalhostAddress());
        System.out.println("host \t"+getHostInetAddress("lichao-MacBook.local"));

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

    public static void main1(String[] args) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }


    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();
            System.out.println(" Name = " + ni.getName());
            System.out.println(" Display Name = " + ni.getDisplayName());
            System.out.println(" Is up = " + ni.isUp());
            System.out.println(" Support multicast = " + ni.supportsMulticast());
            System.out.println(" Is loopback = " + ni.isLoopback());
            System.out.println(" Is virtual = " + ni.isVirtual());
            System.out.println(" Is point to point = " + ni.isPointToPoint());
            System.out.println(" Hardware address = " + Arrays.toString(ni.getHardwareAddress()));
            System.out.println(" MTU = " + ni.getMTU());
            System.out.println("\nList of Interface Addresses:");
            List<InterfaceAddress> list = ni.getInterfaceAddresses();
            for (InterfaceAddress ia : list) {
                System.out.println(" Address = " + ia.getAddress());
                System.out.println(" Broadcast = " + ia.getBroadcast());
                System.out.println(" Network prefix length = " + ia.getNetworkPrefixLength());
                System.out.println("");
            }
        }
    }
}
