import com.google.gson.JsonArray;
import crawler.CrawlerGitHub;
import debug.LocalLog;
import services.GetConfig;
import services.JsonService;
import services.ThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static GetConfig config = GetConfig.getInstance();
    private static LocalLog LOG = LocalLog.getInstance();

    public static void main(String[] argv){
        JsonService service = new JsonService();
        String path = config.getRootPath()+"profiles.json"; // or path = argv[0];

        if (path != null && !path.isEmpty()){
            JsonArray json = service.getJsonFromFile(path);
            if (!json.isJsonNull()){
                AtomicInteger count = new AtomicInteger(1);
                ThreadPool pool = new ThreadPool();
                json.forEach(element ->{
                    String url = element.getAsString();
                    pool.addThread(new Thread(new TaskWorker(url, count.get())));
                    count.getAndIncrement();
                });
                pool.execute();
            }
        }else{
            LOG.error("path to file is empty");
        }
    }
    private static final class TaskWorker implements Runnable {

        private String url;
        private int number;

        TaskWorker(String url, int number){
            this.url = url;
            this.number = number;
        }

        @Override
        public void run() {
            switch (url.substring(url.indexOf('/') + 2, url.indexOf('.'))){
                case "github":
                    String username = url.substring(url.indexOf("m/")+2);
                    CrawlerGitHub crawler = new CrawlerGitHub();
                    String result = crawler.getDataByUsername(username);
                    break;
                default:break;
            }
        }
    }
}
