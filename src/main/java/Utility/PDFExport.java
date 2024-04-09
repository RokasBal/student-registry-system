package Utility;

import StudentData.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.registry.studentregistrysystem.Controller;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static Utility.GetDisplayDate.getMonthLength;
import static com.itextpdf.text.FontFactory.HELVETICA;

public class PDFExport {

    private static Controller controller;
    private static PdfPCell pdfCell;

    public static void exportToPDF(TableView<Student> tableView, String filename, Controller controller) throws IOException, DocumentException {
        PDFExport.controller = controller;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(createTableFromTableView(tableView));
        document.close();
    }

    public static PdfPTable createTableFromTableView(TableView<Student> tableView) throws DocumentException {
        int monthLength = getMonthLength();
        PdfPTable table = new PdfPTable(2 + monthLength);

        float[] widthData = new float[2 + monthLength];
        widthData[0] = 15f;
        widthData[1] = 15f;
        for (int i = 2; i < monthLength + 2; i++) {
            widthData[i] = 2f;
        }
        table.setWidths(widthData);

        table.setWidthPercentage(100f);

        Font cellFont = FontFactory.getFont(HELVETICA);
        cellFont.setSize(6); // Set the font size here

        // Add table headers
        table.addCell(new PdfPCell(new Phrase("First Name", cellFont)));
        table.addCell(new PdfPCell(new Phrase("Last Name", cellFont)));
        for (int i = 0; i < monthLength; i++) {
            table.addCell(new PdfPCell(new Phrase("" + (i + 1), cellFont)));
        }

        ObservableList<Student> students = tableView.getItems();
        for (Student student : students) {
            table.addCell(new PdfPCell(new Phrase(student.getFirstName().getValue(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(student.getLastName().getValue(), cellFont)));
            for (int i = 0; i < getMonthLength(); i++) {
                table.addCell(new PdfPCell(new Phrase(student.getAttendance(controller.getYear(), GetDisplayDate.getMonthIndex(), i + 1), cellFont)));
            }
        }

        return table;
    }
}
