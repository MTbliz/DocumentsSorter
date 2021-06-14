package service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ChangeLocationService {

    public void moveFileToFolder(File file, List<Path> paths) {
        String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
        String fileClassificationNumber = fileNameWithOutExt.split("_")[0];
        for (Path path : paths) {
            String pathName = path.getFileName().toString();
            String pathClassificationNumber = pathName.split("\\s+")[0];
            if (pathClassificationNumber.equals(fileClassificationNumber)) {
                String newFileName = path + "/" + fileNameWithOutExt;
                String[] fileNameSplits = file.getName().split("\\.");
                int extensionIndex = fileNameSplits.length - 1;
                file.renameTo(new File(newFileName + "." + fileNameSplits[extensionIndex]));
            }
        }
    }
}
