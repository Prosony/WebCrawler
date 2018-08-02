package model;

import org.eclipse.egit.github.core.Repository;

public class RepositoryModel {
    private int watchers;
    private Repository repository;
    private String language;

    public RepositoryModel(){}
    public RepositoryModel(int watchers, Repository repository, String language) {
        this.watchers = watchers;
        this.repository = repository;
        this.language = language;
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
