package services;

import debug.LocalLog;

import java.util.LinkedList;

public class ThreadPoolService {

    private LinkedList<Thread> queue = new LinkedList<>();

    private GetConfig config = GetConfig.getInstance();
    private LocalLog LOG = new LocalLog();
    private int maxParallelRequests = config.getMaxParallelRequest();
    private int requestDelayMs = config.getRequestDelayMs();

    /**
     * adds a thread to the pool
     * @param thread
     */
    public void addThread(Thread thread) {
        queue.add(thread);
    }

    /**
     * Starts threads with constraints [maxParallelRequests]
     * and delay between startup [requestDelayMs]
     */
    public void execute() {
        int status = 0;
        int count = 0;
        Thread[] pool = new Thread[maxParallelRequests];
        while (count < queue.size()) {
            if (status < maxParallelRequests) {
                try {
                    Thread thread = queue.get(count);
                    thread.start();
                    LOG.info("[ThreadPoolService][execute] start thread NO: " + count);
                    pool[status] = thread;
                    count++;
                    status++;
                    Thread.sleep(requestDelayMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                LOG.info("[ThreadPoolService][execute] queue is full, wait for threads to complete...");
                while (true) {
                    int threadIsDead = 0;
                    for (int index = 0; index < pool.length; index++) {
                        if (!pool[index].isAlive()) {
                            threadIsDead++;
                        } else {
                            threadIsDead = 0;
                        }
                    }
                    if (threadIsDead == pool.length) {
                        status = 0;
                        break;
                    }
                }
            }
        }
    }
}
