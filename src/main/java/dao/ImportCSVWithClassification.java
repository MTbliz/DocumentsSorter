package dao;

import entity.DocumentClassification;
import exception.ColumnIndexNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ImportCSVWithClassification {

    private String csvSplitBy = ";";
    private String line = "";
    private static String DOCUMENT_SECTION_COLUMN = "Section";
    private static String DOCUMENT_SUBSECTION_COLUMN = "Subsection";
    private static String DOCUMENT_CLASSIFICATION_COLUMN = "Classification";


    public List<DocumentClassification> readDocumentsPropertiesFromCsvFile(String csvFile) {
        int documnetSectionColumnIndex;
        int documnetSubsectionColumnIndex;
        int documentClassificationColumnIndex;

        List<DocumentClassification> documentClassifications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] headers = br.readLine().split(csvSplitBy);
            documnetSectionColumnIndex = indexOfColumn(headers, DOCUMENT_SECTION_COLUMN);
            documnetSubsectionColumnIndex = indexOfColumn(headers, DOCUMENT_SUBSECTION_COLUMN);
            documentClassificationColumnIndex = indexOfColumn(headers, DOCUMENT_CLASSIFICATION_COLUMN);

            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                DocumentClassification documentClassification = new DocumentClassification(
                        row[documnetSectionColumnIndex],
                        row[documnetSubsectionColumnIndex],
                        row[documentClassificationColumnIndex]
                );
                documentClassifications.add(documentClassification);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ColumnIndexNotFoundException e) {
            e.printStackTrace();
        }
        return documentClassifications;
    }

    private static <T> int indexOfColumn(T[] arr, T val) throws ColumnIndexNotFoundException {
        int index;
        index = Arrays.asList(arr).indexOf(val);
        if (index == -1) {
            throw new ColumnIndexNotFoundException("Column: " + val + " not found");
        } else {
            return index;
        }
    }
}
