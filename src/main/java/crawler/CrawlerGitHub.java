package crawler;

import com.google.gson.JsonObject;
import debug.LocalLog;
import model.RepositoryModel;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import services.GetConfig;
import services.URLService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CrawlerGitHub {

    private GetConfig config = GetConfig.getInstance();
    private LocalLog LOG = new LocalLog();

    public String getDataByUsername(String url) {
        String username = new URLService().getUsername(url);
        try {
            JsonObject json = new JsonObject();
            GitHubClient client = new GitHubClient();
            RepositoryService service = new RepositoryService(client);

            client.setCredentials(config.getUsername(), config.getPassword()); //authentication GitHub
            UserService serviceUser = new UserService(client);

            User user = serviceUser.getUser(username);
            json.addProperty("username", user.getLogin());
            json.addProperty("name", user.getName());
            json.addProperty("company", user.getCompany());
            json.addProperty("location", user.getLocation());

            List<Repository> list = service.getRepositories(username);
            RepositoryModel model = getMostPopularRepo(list);
            String mostUsedLanguage = getMostUsedLang(list);

            json.addProperty("language", mostUsedLanguage);

            if (model != null) {
                json.addProperty("repository", model.getRepository().getName());
                json.addProperty("watcher", model.getWatchers());
                json.addProperty("language-repo", model.getLanguage());
            } else {
                json.addProperty("repository", "null");
                json.addProperty("watcher", "null");
                json.addProperty("language-repo", "null");
            }
            return json.toString();
        } catch (IOException e) {
            if (e.getMessage().equals("Bad credentials (401)")) {
                LOG.error("[CrawlerGitHub][getDataByUsername] " + e.getMessage() + ". Check USERNAME and PASSWORD by GitHub.com in [global-config.properties]");
            }
        }
        return null;
    }

    private RepositoryModel getMostPopularRepo(List<Repository> list) {

        RepositoryModel repoModel = new RepositoryModel();

        list.forEach(repository -> {
            int watcher = repository.getWatchers();
            if (repoModel.getWatchers() < watcher) {
                repoModel.setWatchers(watcher);
                repoModel.setRepository(repository);
                repoModel.setLanguage(repository.getLanguage());
            }
        });

        if (repoModel.getRepository() != null) {
            return repoModel;
        } else {
            return null;
        }
    }

    private String getMostUsedLang(List<Repository> list) {

        HashMap<String, Integer> indexLang = new HashMap<>();

        list.forEach(repository -> {
            String language = repository.getLanguage();
            Integer object = indexLang.get(language);
            if (object != null) {
                int index = object + 1;
                indexLang.remove(language);
                indexLang.put(language, index);
            } else {
                if (language != null) {
                    indexLang.put(language, 0);
                }
            }
        });

        AtomicInteger cache = new AtomicInteger();
        AtomicReference<String> lang = new AtomicReference<>();

        indexLang.forEach((language, index) -> {
            if (cache.get() < index) {
                cache.set(index);
                lang.set(language);
            }
        });
        return lang.get();
    }
}
