package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public abstract class Downloader {

    public static final String HTML_EXTENTION = ".html";
    public static final String DIRECTORY_DOWNLOAD = "./download/";

    public abstract int process(List<String> urls, List<String> titles);

    public String saveUrl2File(String urlString, String title) {
        InputStream is = null;
        OutputStream os = null;
        String fileName = "";
        try {
            URL url4download = new URL(urlString);
            is = url4download.openStream();

            fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
            if (fileName.isEmpty()) {
                fileName = url4download.getHost() + HTML_EXTENTION;
            }

            fileName = title.replaceAll("[:\\\\/*?|<>\"]", "") + HTML_EXTENTION;
            try{
                os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName);
            }catch (FileNotFoundException e){
                System.err.println("Illegal Filename" + fileName);
            }

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                try {
                    os.write(b, 0, length);
                } catch (NullPointerException e){
                    System.err.println("Cant Write to Output!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(is).close();
                Objects.requireNonNull(os).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
