package services;

import debug.LocalLog;

import java.util.LinkedList;

public class ThreadPool {

    private LinkedList<Thread> queue = new LinkedList<>();
    private GetConfig config = GetConfig.getInstance();
    private LocalLog LOG = LocalLog.getInstance();
    private int maxParallelRequests = config.getMaxParallelRequest();
    private int requestDelayMs = config.getRequestDelayMs();

    public void addThread(Thread thread){
        queue.add(thread);
    }

    public void execute(){

        int status = 0;
        int count = 0;
        //        for (int count = 0; count < queue.size(); count++)
        Thread[] pool = new Thread[maxParallelRequests];
        while (count < queue.size()){
            if (status < maxParallelRequests){
                try {
                    Thread thread = queue.get(count);
                    thread.start();
                    LOG.info("start thread NO: "+count);
                    pool[status] = thread;
                    count++;
                    status++;
                    Thread.sleep(requestDelayMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                LOG.info("wait thread");
                while(true){
                    int threadIsDead = 0;
                    for (int index = 0; index < pool.length; index++){
                        if (!pool[index].isAlive()){
                            threadIsDead++;
                        }else{
                            threadIsDead = 0;
                        }
                    }
                    if (threadIsDead == pool.length){
                        status = 0;
                        break;
                    }
                }
            }
        }
//        queue.getFirst().start();
    }


}
