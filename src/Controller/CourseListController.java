package Controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseListController implements Initializable{
    public TextArea display;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String url = "http://coursefinder.utoronto.ca/";
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(url);

            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.waitForBackgroundJavaScript(5000);

            System.out.println(page.getElementById("u281_line20").getNodeName());
            System.out.println(page.getElementById("u281_line20").getNodeType());
            HtmlPage cs = page.getElementById("u281_line20").click();
            display.setText(cs.asText());




        } catch (IOException ex ) {
            ex.printStackTrace();
        }
    }

}
