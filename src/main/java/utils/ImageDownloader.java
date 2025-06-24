package utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.net.URL;
import java.util.List;

public class ImageDownloader {
    public void downloadImages(List<String> imageUrls, List<String> titles) {
        for (int i = 0; i < imageUrls.size(); i++) {
            String imageUrl = imageUrls.get(i);
            if (!imageUrl.isEmpty()) {
                try {
                    String fileName = "images/" + titles.get(i).replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                    FileUtils.copyURLToFile(new URL(imageUrl), new File(fileName));
                    System.out.println("Downloaded image: " + fileName);
                } catch (Exception e) {
                    System.out.println("Failed to download image: " + imageUrl);
                }
            }
        }
    }
}