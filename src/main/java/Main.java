import com.google.gson.JsonArray;
import crawler.CrawlerGitHub;
import debug.LocalLog;
import services.GetConfig;
import services.JsonService;
import services.ThreadPoolService;
import services.URLService;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static GetConfig config = GetConfig.getInstance();
    private static LocalLog LOG = new LocalLog();

    public static void main(String[] argv) {
        JsonService service = new JsonService();
        String path;
        if (argv.length != 0 && argv.length < 2) {
            path = argv[0];
        } else {
            path = config.getRootPath() + "profiles.json";
        }
        File file = new File(path);
        if (file.isFile() && file.canRead()) {
            JsonArray json = service.getJsonFromFile(path);
            if (!json.isJsonNull()) {

                AtomicInteger count = new AtomicInteger(1);
                ThreadPoolService pool = new ThreadPoolService();

                json.forEach(element -> {
                    String url = element.getAsString();
                    pool.addThread(new Thread(new TaskWorker(url, count.get())));
                    count.getAndIncrement();
                });
                pool.execute();
            } else {
                LOG.error("[Main][main] invalid file!");
            }
        } else {
            LOG.error("[Main][main] is not a file or can not be read!");
        }
    }

    private static final class TaskWorker implements Runnable {

        private String url;
        private int number;

        TaskWorker(String url, int number) {
            this.url = url;
            this.number = number;
        }

        @Override
        public void run() {
            URLService urlService = new URLService();
            if (urlService.checkGitHubURL(url)) {
                String domain = urlService.getDomain(url);
                if (domain != null) {
                    LOG.info("[TaskWorker][run] domain: " + domain);
                    switch (domain) {
                        case "github":
                            CrawlerGitHub crawler = new CrawlerGitHub();
                            String result = crawler.getDataByUsername(url); //TODO something with data
                            LOG.success("[TaskWorker][run] thread N0: " + number + " result: " + result);
                            break;
                        case "SOMESITE":
                            //TODO
                            break;
                        default:
                            break;
                    }
                } else {
                    LOG.error("[TaskWorker][run] invalid domain url: " + url);
                }
            }
        }
    }
}
