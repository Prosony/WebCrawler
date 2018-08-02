package model;

public class UserModel {
    private String username;
    private String name;
    private String company;
    private String location;
    private String lang;
    private String repo;
    private String starsRepo;
    private String langRepo;

    public UserModel(String username, String name, String company, String location, String lang, String repo, String starsRepo, String langRepo) {
        this.username = username;
        this.name = name;
        this.company = company;
        this.location = location;
        this.lang = lang;
        this.repo = repo;
        this.starsRepo = starsRepo;
        this.langRepo = langRepo;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRepo() {
        return repo;
    }
    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getStarsRepo() {
        return starsRepo;
    }
    public void setStarsRepo(String starsRepo) {
        this.starsRepo = starsRepo;
    }

    public String getLangRepo() {
        return langRepo;
    }
    public void setLangRepo(String langRepo) {
        this.langRepo = langRepo;
    }
}
