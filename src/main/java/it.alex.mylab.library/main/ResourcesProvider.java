package it.alex.mylab.library.main;

import java.io.File;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcesProvider implements FileProvider{
    @Override
    public String getFile(String whatForFile) {
        String folder = "it/alex/mylab/library";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        path = path.replaceAll("%20", " ");
        File[] listFile = new File(path).listFiles();
        Pattern findFile = Pattern.compile(whatForFile+".txt");
        for (int i = 0; i < listFile.length; i++) {
            Matcher matcherFind = findFile.matcher(listFile[i].getName());
            if (matcherFind.find()){
                return listFile[i].getPath();
            }
        }
        return "";
    }
}