package com.lichao666.sshj;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lichao
 * @date 2021/2/24
 */
public class SshjTestCase {

    @Test
    public void test1() throws IOException {
        SSHClient sourceClient = new SSHClient();
        sourceClient.addHostKeyVerifier(new PromiscuousVerifier());

        sourceClient.connect("192.168.66.130", 22);
        sourceClient.authPassword("root", "123456");

        String command = "sleep 10";

        try (Session session = sourceClient.startSession()) {
            System.out.println("连接成功");
            Session.Command cmd = session.exec(command);
//            String result = IOUtils.readFully(cmd.getInputStream()).toString();
            InputStream in = cmd.getInputStream();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (cmd.isEOF()) {
                    System.out.println("退出状态: " + cmd.getExitStatus());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        sourceClient.close();
    }

    public void execute(int index) throws Exception{
        SSHClient sourceClient = new SSHClient();
        sourceClient.addHostKeyVerifier(new PromiscuousVerifier());

        sourceClient.connect("192.168.66.130", 22);
        sourceClient.authPassword("root", "123456");

        String command = " echo "+index+" && sleep 10";

        try (Session session = sourceClient.startSession()) {

            Session.Command cmd = session.exec(command);
//            String result = IOUtils.readFully(cmd.getInputStream()).toString();
            InputStream in = cmd.getInputStream();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (cmd.isEOF()) {
                    System.out.println("退出状态: " + cmd.getExitStatus());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        sourceClient.disconnect();
    }


    public static void main(String[] args) throws Exception {
        int threadNumber = 500;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        final AtomicInteger success = new AtomicInteger();
        final AtomicInteger failed = new AtomicInteger();
        // 模拟多个线程同时操作
        final SshjTestCase sshjTestCase = new SshjTestCase();
        for(int i=0;i< threadNumber;i++){
            final int finalI = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        sshjTestCase.execute(finalI);
                        success.incrementAndGet();
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        failed.incrementAndGet();
                        e.printStackTrace();
                        countDownLatch.countDown();
                    }
                }
            });
            t.start();
        }

        countDownLatch.await();

        System.out.println("成功连接数"+success);
        System.out.println("失败连接数"+failed);


    }

}
