package sandbox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MakeMyTestData {

	public static void main(String[] args) {
		int FILE_CNT = 3;
		int ROW_MAX = 10;

		String log_msg = "TEST";

		List<String> rowDataList = new ArrayList<String>();

		MyFileUtil util = new MyFileUtil();

		for (int i = 0; i <= FILE_CNT; i++) {

			String seq = String.format("%03d", i);
			LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 0, 0, 0).plusSeconds(i);
			DateTimeFormatter dtf_logDate = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
			DateTimeFormatter dff_fileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
			String fileTime = ldt.format(dff_fileName);
			String logDate = ldt.format(dtf_logDate);
			String fileName = fileTime + "_" + seq + ".txt";

			for (int j = 0; j < ROW_MAX; j++) {

				RowData r = new RowData(j, logDate, log_msg);
				rowDataList.add(r.getCSVText());
			}

			util.outputFile(rowDataList, fileName);
			rowDataList.clear();
		}

	}

}

class RowData {
	String splitStr = " : ";

	int rowNo;

	String logDate;

	String logMsg;

	public RowData() {
	}

	public RowData(int rowNo, String logDate, String logMsg) {
		this.rowNo = rowNo;
		this.logDate = logDate;
		this.logMsg = logMsg;
	}

	public String getCSVText() {
		return String.format("%05d", this.rowNo) + splitStr + this.logDate + splitStr + this.logMsg;
	}

}

class MyFileUtil{
	public MyFileUtil() {
	}
	public void outputFile(List<String> list,String fileName) {
		File f = new File("I:/"+fileName);
		Path p = f.toPath();
		try {
			Files.write(p
						, list, java.nio.file.StandardOpenOption.CREATE
						,java.nio.file.StandardOpenOption.WRITE,java.nio.file.StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}