import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class responsible for analyzing the HTML of a web page and finding the deepest text.
 */
public class HtmlAnalyzer {

    /**
     * Main method of the application.
     * 
     * @param args Command line arguments. Should contain the URL of the web page to be analyzed.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: URL not provided.");
            System.out.println("Usage: java HtmlAnalyzer <url>");
            return;
        }

        String url = args[0];
        try {
            String pageHtml = readPageHtml(url);
            if (pageHtml == null) {
                System.out.println("Error: Unable to read the page.");
                return;
            }

            String deepestText = findDeepestText(pageHtml);
            if (deepestText == null) {
                System.out.println("Error: Unable to find the deepest text.");
                return;
            }

            System.out.println("Deepest text: " + deepestText);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Reads the HTML of a web page.
     * 
     * @param url URL of the web page to be read.
     * @return HTML of the web page.
     * @throws IOException If an error occurs while reading the page.
     */
    private static String readPageHtml(String url) throws IOException {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            int response = connection.getResponseCode();
            if (response == 200) {
                return readPageContent(connection);
            } else {
                throw new IOException("Error reading the page: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error reading the page: " + e.getMessage());
            return null;
        }
    }

    /**
     * Reads the content of a web page.
     * 
     * @param connection HTTP connection with the web page.
     * @return Content of the web page.
     * @throws IOException If an error occurs while reading the content.
     */
    private static String readPageContent(HttpURLConnection connection) throws IOException {
        StringBuilder pageContent;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            pageContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                pageContent.append(line);
            }
        }
        return pageContent.toString();
    }

    /**
     * Finds the deepest text in an HTML document.
     * 
     * @param pageHtml HTML of the web page.
     * @return Deepest text.
     */
    private static String findDeepestText(String pageHtml) {
        int maxDepth = 0;
        String deepestText = "";
        int currentDepth = 0;
        StringBuilder currentText = new StringBuilder();
        boolean isTag = false;
        boolean isMalformed = false;

        // Iterate through the HTML document to find the deepest text
        for (int i = 0; i < pageHtml.length(); i++) {
            char c = pageHtml.charAt(i);

            // Check if the current character is the start of a tag
            if (c == '<') {
                isTag = true;

                // Check if the tag is an opening or closing tag
                if (pageHtml.charAt(i + 1) != '/') {
                    currentDepth++;
                } else {
                    currentDepth--;
                }

                // Check if the HTML document is malformed
                if (currentDepth < 0) {
                    isMalformed = true;
                    break;
                }
            } else if (c == '>') {
                isTag = false;
            } else if (!isTag) {
                // Check if the current depth is greater than the maximum depth
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestText = "";
                    currentText.setLength(0);
                }

                // Append the current character to the current text
                currentText.append(c);

                // Check if the current depth is equal to the maximum depth
                if (currentDepth == maxDepth) {
                    deepestText = currentText.toString().trim();
                }
            }
        }

        // Check if the HTML document is malformed
        if (isMalformed) {
            return "Malformed HTML";
        }

        return deepestText;
    }
}