package model;

import org.eclipse.egit.github.core.Repository;

public class RepositoryModel {
    private int watchers;
    private Repository repository;
    private String language;

    public RepositoryModel() {
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
