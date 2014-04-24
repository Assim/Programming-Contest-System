package util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.Contestant;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public final class PDFGenerator {

	/**
	 * Generates a PDF report for the data stored on the server DB.
	 */
	public static void generateReport() {
		try {
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);
			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);

			float y; // last Y-coordinate

			y = write(page, contentStream, 0, "Programming Contest System\n",
					25, PDType1Font.HELVETICA_BOLD_OBLIQUE);

			y = write(page, contentStream, y, " ", 12, PDType1Font.HELVETICA); // empty
																				// line
			y = write(page, contentStream, y, " ", 12, PDType1Font.HELVETICA); // empty
																				// line

			y = write(page, contentStream, y, "Statistics:", 18,
					PDType1Font.HELVETICA);
			y = write(page, contentStream, y, "Contestants: "
					+ DbAdapter.getAllContestants().size(), 12,
					PDType1Font.HELVETICA);
			y = write(page, contentStream, y, "Submissions: "
					+ DbAdapter.getAllSubmissions(null).size(), 12,
					PDType1Font.HELVETICA);

			y = write(page, contentStream, y, " ", 12, PDType1Font.HELVETICA); // empty
																				// line
			y = write(page, contentStream, y, " ", 12, PDType1Font.HELVETICA); // empty
																				// line

			y = write(page, contentStream, y, "Scoreboard:", 18,
					PDType1Font.HELVETICA);

			ArrayList<Contestant> scoreboard = DbAdapter.getScoreboard();

			String[][] content = new String[scoreboard.size() + 1][3];

			// Table headers
			content[0][0] = "Contestant";
			content[0][1] = "Problems Solved";
			content[0][2] = "Penalty";

			int i = 1; // start from one after header row

			// Fill table with scoreboard data
			for (Contestant c : scoreboard) {
				content[i][0] = c.getUsername();
				content[i][1] = String.valueOf(c.getProblemsSolved());
				content[i][2] = String.valueOf(c.getPenalty());
				i++;
			}

			drawTable(page, contentStream, y, content, 12,
					PDType1Font.HELVETICA);

			contentStream.close();

			// Create the file name based on the current date and time.
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HHmm");
			Date date = new Date();
			String fileName = "PCS Report - " + dateFormat.format(date)
					+ ".pdf";

			doc.save(fileName);

			doc.close();

			// Show success message
			JOptionPane.showMessageDialog(null,
					"PDF was generated and saved with the name: " + fileName);
		} catch (IOException | COSVisitorException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There was an error in generate the report.");
		}
	}

	/**
	 * This method writes text to the page an returns the y coordinate of where
	 * the text has stopped. It takes a PDPage object, and a content stream
	 * object which will be used to add the text. It also takes a text which
	 * will be written, and it will automatically format it into multiple lines.
	 * It will also require a font size and font. The last is the Y-coordinate
	 * value, this is where to start, if 0 was given, it will start from the
	 * starting point based on the margin given. It will return a float value,
	 * that is where the Y-coordinate stopped at, if you require multiple more
	 * writing, then the return value of one call should be the Y-coordinate
	 * value of the next call.
	 */
	private static float write(PDPage page, PDPageContentStream contentStream,
			float y, String text, float fontSize, PDFont pdfFont) {
		// This is the result Y-coordinate value.
		float res = 0;
		try {
			float leading = 1.5f * fontSize;

			PDRectangle mediaBox = page.findMediaBox();
			float margin = 72;
			float width = mediaBox.getWidth() - 2 * margin;
			float startX = mediaBox.getLowerLeftX() + margin;
			float startY = mediaBox.getUpperRightY() - margin;
			if (y > 0) // Replace startY with value of Y if Y is more than 0
				startY = y;

			// Result should be the starting value of Y
			res = startY;

			List<String> lines = new ArrayList<String>();
			int lastSpace = -1;
			while (text.length() > 0) {
				int spaceIndex = text.indexOf(' ', lastSpace + 1);
				if (spaceIndex < 0) {
					lines.add(text);
					text = "";
				} else {
					String subString = text.substring(0, spaceIndex);
					float size = fontSize * pdfFont.getStringWidth(subString)
							/ 1000;
					if (size > width) {
						if (lastSpace < 0) // So we have a word longer than the
											// line... draw it anyways
							lastSpace = spaceIndex;
						subString = text.substring(0, lastSpace);
						lines.add(subString);
						text = text.substring(lastSpace).trim();
						lastSpace = -1;
					} else {
						lastSpace = spaceIndex;
					}
				}
			}

			contentStream.beginText();
			contentStream.setFont(pdfFont, fontSize);
			contentStream.moveTextPositionByAmount(startX, startY);
			for (String line : lines) {
				contentStream.drawString(line);
				res -= leading; // Deduct the leading so that we can know where
								// we stopped when finishing
				contentStream.moveTextPositionByAmount(0, -leading);
			}
			contentStream.endText();

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There was an error in generate the report.");
		}

		return res;
	}

	/**
	 * This method draws a table on the PDF file. It will take the Y-coordinate
	 * and a two-dimensional array containing the data of the table. Also, the
	 * font size and font type should be specified in the method call.
	 */
	private static void drawTable(PDPage page,
			PDPageContentStream contentStream, float y, String[][] content,
			float fontSize, PDFont pdfFont) throws IOException {
		final float margin = 100;
		final int rows = content.length;
		final int cols = content[0].length;
		final float rowHeight = 20f;
		final float tableWidth = page.findMediaBox().getWidth() - (2 * margin);
		final float tableHeight = rowHeight * rows;
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		// draw the rows
		float nexty = y;
		for (int i = 0; i <= rows; i++) {
			contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
			nexty -= rowHeight;
		}

		// draw the columns
		float nextx = margin;
		for (int i = 0; i <= cols; i++) {
			contentStream.drawLine(nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}

		// now add the text
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

		float textx = margin + cellMargin;
		float texty = y - 15;
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				String text = content[i][j];
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(text);
				contentStream.endText();
				textx += colWidth;
			}
			texty -= rowHeight;
			textx = margin + cellMargin;
		}
	}

}