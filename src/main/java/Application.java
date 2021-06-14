import dao.ImportCSV;
import dao.ImportCSVWithClassification;
import entity.DocumentClassification;
import entity.DocumentProperties;
import service.ChangeLocationService;
import service.FolderStructureCreator;
import service.RenameFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        String absolutePath = "testfiles";
        String csvFileName = "TMF.csv";
        String documentsClassificationCsvName = "FolderStructure.csv";

        //import document section, subsection, classification
        ImportCSVWithClassification importCSVWithClassification = new ImportCSVWithClassification();
        List<DocumentClassification> documentClassifications = new ArrayList<>();
        documentClassifications = importCSVWithClassification.readDocumentsPropertiesFromCsvFile(documentsClassificationCsvName);

        //create folder structure
        FolderStructureCreator folderStructureCreator = new FolderStructureCreator();
        List<Path> paths = folderStructureCreator.createFolderStructure(documentClassifications);

        //import documents properties
        ImportCSV importCSV = new ImportCSV();
        Map<String, DocumentProperties> documentPropertiesMap = importCSV.readDocumentsPropertiesFromCsvFile(csvFileName);

        //Rename File according to CSV File
        File dir = new File(absolutePath);
        File[] filesInDir = dir.listFiles();
        for (File file : filesInDir) {
            String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
            for (Map.Entry<String, DocumentProperties> dp : documentPropertiesMap.entrySet()) {
                if (dp.getKey().equals(fileNameWithOutExt)) {
                    //Specify naming convention
                    String newFileName = dp.getValue().getClassification().split("\\s+")[0] +
                            "_" + dp.getValue().getDocumentNumber() + "_" + dp.getValue().getDocumentTitle() + "_" + dp.getValue().getDocumentDate();
                    RenameFile.renameFile(file, newFileName);
                }
            }
        }

        //Move file to proper folder
        File[] renamedFilesInDirectory = dir.listFiles();
        for (File file : renamedFilesInDirectory) {
            ChangeLocationService changeLocationService = new ChangeLocationService();
            changeLocationService.moveFileToFolder(file, paths);
        }
    }
}

