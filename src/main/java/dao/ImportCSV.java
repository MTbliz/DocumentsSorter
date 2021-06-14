package dao;

import entity.DocumentProperties;
import exception.ColumnIndexNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImportCSV {
    private String csvSplitBy = ";";
    private String line = "";
    private static String DOCUMENT_NAME_COLUMN = "Name";
    private static String DOCUMENT_TITLE_COLUMN = "Title";
    private static String DOCUMENT_NUMBER_COLUMN = "Document Number";
    private static String DOCUMENT_DATE_COLUMN = "Document Date";
    private static String DOCUMENT_SECTION_COLUMN = "Type";
    private static String DOCUMENT_SUBSECTION_COLUMN = "Subtype";
    private static String DOCUMENT_CLASSIFICATION_COLUMN = "Classification";


    public Map<String, DocumentProperties> readDocumentsPropertiesFromCsvFile(String csvFile) {
        int documentNameColumnIndex;
        int documentTitleColumnIndex;
        int documentNumberColumnIndex;
        int documentDateColumnIndex;
        int documnetSectionColumnIndex;
        int documnetSubsectionColumnIndex;
        int documentClassificationColumnIndex;

        Map<String, DocumentProperties> documentPropertiesMapToDocumentNumber = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] headers = br.readLine().split(csvSplitBy);
            documentNameColumnIndex = indexOfColumn(headers, DOCUMENT_NAME_COLUMN);
            documentTitleColumnIndex = indexOfColumn(headers, DOCUMENT_TITLE_COLUMN);
            documentNumberColumnIndex = indexOfColumn(headers, DOCUMENT_NUMBER_COLUMN);
            documentDateColumnIndex = indexOfColumn(headers, DOCUMENT_DATE_COLUMN);
            documnetSectionColumnIndex = indexOfColumn(headers, DOCUMENT_SECTION_COLUMN);
            documnetSubsectionColumnIndex = indexOfColumn(headers, DOCUMENT_SUBSECTION_COLUMN);
            documentClassificationColumnIndex = indexOfColumn(headers, DOCUMENT_CLASSIFICATION_COLUMN);

            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                DocumentProperties documentProperties = new DocumentProperties(
                        row[documentNameColumnIndex],
                        row[documentTitleColumnIndex],
                        row[documentNumberColumnIndex],
                        row[documentDateColumnIndex],
                        row[documnetSectionColumnIndex],
                        row[documnetSubsectionColumnIndex],
                        row[documentClassificationColumnIndex]
                );
                documentPropertiesMapToDocumentNumber.put(documentProperties.getDocumentNumber(), documentProperties);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ColumnIndexNotFoundException e) {
            e.printStackTrace();
        }
        return documentPropertiesMapToDocumentNumber;
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
