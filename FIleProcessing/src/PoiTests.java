import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import java.io.FileInputStream;
import java.util.logging.Logger;
public class PoiTests {
    static final Logger logger = Logger.getLogger(String.valueOf(PoiTests.class));
    static final String LOG_PROPERTIES_FILE = "log4j.properties";
    public static void main(String[] args) throws Exception{


        try{

            XWPFDocument docx = new XWPFDocument((new FileInputStream("C:/Users/User/Desktop/Demo2.docx")));
            XWPFWordExtractor we = new XWPFWordExtractor(docx);
            System.out.print(we.getText());

            String smallfile = we.getText();
            String[] swearwords = smallfile.split(" ");
            for(String s:swearwords){
                System.out.println(s);
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
