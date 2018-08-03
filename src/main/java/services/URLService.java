package services;

public class URLService {
    private static final String patternGitHub = "(http(s)?://)?(www.)?(github.com/)([A-Z-a-z0-9])*";

    public boolean checkGitHubURL(String url) {
        return url.matches(patternGitHub);
    }

    public String getDomain(String url) {
        if (url.matches("((http)(s)?://)?(www.)([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(url.indexOf("w.") + 2, url.indexOf(".c"));
        }
        if (url.matches("((http)(s)?://)([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(url.indexOf("//") + 2, url.indexOf(".c"));
        }
        if (url.matches("([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(0, url.indexOf("."));
        }
        return null;
    }

    public String getUsername(String url) {
        if (url.matches("((http)(s)?://)?(www.)([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(url.indexOf("com/") + 4);
        }
        if (url.matches("((http)(s)?://)([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(url.indexOf("com/") + 4);
        }
        if (url.matches("([a-z]+)(.com)(/)([A-Z-a-z0-9])*")) {
            return url.substring(url.indexOf("com/") + 4);
        }
        return null;
    }
}
