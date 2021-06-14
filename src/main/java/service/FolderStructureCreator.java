package service;

import entity.DocumentClassification;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FolderStructureCreator {

    private String absoluteDirectoryPath = "";

    public List<Path> createFolderStructure(List<DocumentClassification> documentClassifications) {
        createMainFolder();
        return createClassifications(documentClassifications);
    }

    private void createMainFolder() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String folderName = "folder_" + dateFormat.format(date);
        File mainFolder = Paths.get(FileSystemView.getFileSystemView()
                        .getHomeDirectory()
                        .getAbsolutePath(),
                folderName).toFile();
        setMainFolderAbsolutePath(mainFolder);
        mainFolder.mkdir();
    }

    private void setMainFolderAbsolutePath(File file) {
        absoluteDirectoryPath = file.getAbsolutePath();
    }

    private List<Path> createClassifications(List<DocumentClassification> documentClassifications) {
        List<Path> paths = new ArrayList<>();
        documentClassifications.forEach(documentClassification -> {
            try {
                Path path = Paths.get(absoluteDirectoryPath + "/" + documentClassification.getSection() +
                        "/" + documentClassification.getSubsection() + "/" + documentClassification.getClassification());
                String pathName = path.getFileName().toString();
                paths.add(path);
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return paths;
    }

    private void createSections(List<DocumentClassification> documentClassifications) {
        List<String> sections = documentClassifications.stream().map(DocumentClassification::getSection).distinct().collect(Collectors.toList());
        sections.forEach(section -> {
            try {
                Files.createDirectories(Paths.get(absoluteDirectoryPath + "/" + section));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void createSubsections(List<DocumentClassification> documentClassifications) {
        List<String> sections = documentClassifications.stream().map(DocumentClassification::getSection).distinct().collect(Collectors.toList());
        List<String> subsections = documentClassifications.stream().map(DocumentClassification::getSubsection).distinct().collect(Collectors.toList());
        for (String subsection : subsections) {
            String[] row = subsection.split("\\.");
            String sectionNumber = row[0];
            for (String section : sections) {
                if (section.split("\\s+")[0].equals(sectionNumber)) {
                    try {
                        Files.createDirectories(Paths.get(absoluteDirectoryPath + "/" + section + "/" + subsection));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
