package com.amigoscode.bughunt.medium.bug60;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Processes CSV data provided as an Iterator of lines.
 * Supports counting rows, extracting headers, and collecting all data rows.
 *
 * Typical usage:
 *   Iterator<String> lines = readCsvFile("report.csv");
 *   CsvReport report = new CsvReport(lines);
 *   int total = report.rowCount();
 *   List<String> headers = report.headers();
 *   List<List<String>> data = report.dataRows();
 */
public class CsvReport {

    private final Iterator<String> rows;

    public CsvReport(Iterator<String> rows) {
        if (rows == null) {
            throw new IllegalArgumentException("rows iterator must not be null");
        }
        this.rows = rows;
    }

    /**
     * Returns the total number of rows (including the header row).
     * BUG: consumes the entire iterator — subsequent calls to
     * headers() or dataRows() see an exhausted iterator.
     */
    public int rowCount() {
        int count = 0;
        while (rows.hasNext()) {
            rows.next();
            count++;
        }
        return count;
    }

    /**
     * Returns the column headers (first row, split by comma).
     */
    public List<String> headers() {
        if (rows.hasNext()) {
            String headerLine = rows.next();
            return List.of(headerLine.split(","));
        }
        return List.of();
    }

    /**
     * Returns all data rows (everything after the header), each split by comma.
     */
    public List<List<String>> dataRows() {
        List<List<String>> result = new ArrayList<>();
        boolean firstSkipped = false;
        while (rows.hasNext()) {
            String line = rows.next();
            if (!firstSkipped) {
                firstSkipped = true;
                continue; // skip header
            }
            result.add(List.of(line.split(",")));
        }
        return result;
    }

    /**
     * Returns the value at a specific row and column index (0-based, excluding header).
     */
    public String valueAt(int row, int col) {
        List<List<String>> data = dataRows();
        if (row < 0 || row >= data.size()) {
            throw new IndexOutOfBoundsException("Row " + row + " out of bounds");
        }
        List<String> rowData = data.get(row);
        if (col < 0 || col >= rowData.size()) {
            throw new IndexOutOfBoundsException("Col " + col + " out of bounds");
        }
        return rowData.get(col);
    }

    /**
     * Returns a summary: "N rows, M columns".
     */
    public String summary() {
        int rows = rowCount();
        List<String> hdrs = headers();
        return rows + " rows, " + hdrs.size() + " columns";
    }
}
