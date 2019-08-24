package com.team.house.test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class TestGetIP {

    public static void main(String[] args) {

        TestGetIP testGetIP = new TestGetIP();
        testGetIP.printAddresses();

        System.out.println("\n\n\n\n");

        System.out.println(testGetIP.getIP());

    }

    // 找一个合理的ipv4
    public String getIP() {
        try {
            // 根据hostname找ip
            InetAddress address = InetAddress.getLocalHost();
            if (address.isLoopbackAddress()) {
                Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                while (allNetInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress ip = addresses.nextElement();
                        if (!ip.isLinkLocalAddress() && !ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 所有网络接口
    public void printAddresses() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

                // 去除回环接口，子接口，未运行和接口
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null) {

                        System.out.println("ip = " + ip.getHostAddress());
                        // ipv4
                        if (ip instanceof Inet4Address) {
                            System.out.println("ipv4 = " + ip.getHostAddress());

                            if (ip.getHostAddress().startsWith("192") || ip.getHostAddress().startsWith("10")
                                    || ip.getHostAddress().startsWith("172") || ip.getHostAddress().startsWith("169")) {
                                // 内网
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error when getting host ip address"+ e.getMessage());
        }
    }
}

