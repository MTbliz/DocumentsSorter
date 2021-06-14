package service;

import java.io.File;

public class RenameFile {

    public static void renameFile(File file, String newName) {
        String[] fileNameSplits = file.getName().split("\\.");
        int extensionIndex = fileNameSplits.length - 1;
        String absolutePath = file.getParent();
        file.renameTo(new File(absolutePath + "/" + newName + "." + fileNameSplits[extensionIndex]));
    }
}
