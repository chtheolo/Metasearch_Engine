package metasearchengine;

import org.jsoup.helper.Validate;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Yahoo {
    
    public String[] main(String UrlSearch2) {
        
        int UrlNumber = 1;
        String UrlSearch1 = "gr.search.yahoo.com/search?p=";
        //String UrlSearch2 = "space";
        String UrlSearch3 = "&fr=yfp-t-921";
        String UrlSearch =UrlSearch1+UrlSearch2+UrlSearch3;
        String[] YahooLinks = new String[100];
        
        do{
    
        Document doc;
        try{
            
            doc = Jsoup.connect("https://"+UrlSearch).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
            Elements Contents = doc.select("ol[class=mb-15 reg searchCenterMiddle]"); //Se ayto to shmeio ths HTML vriskonte ta epimerous Url pou theloume
        
            Elements GetUrlArea = Contents.select("li");
            for ( Element GetUrlElement : GetUrlArea) { 
                if(GetUrlElement.parent() == GetUrlArea.first().parent() && UrlNumber <= 100){ //Elegxos gia na pairnw mono to Kyrio Url kai oxi ta "Ypo-Url"
                    Elements UrlElement = GetUrlElement.select("span");
                    String Url;
                    if(UrlElement.hasText()){ //Elegxos an yparxei text giati an den yparxei to programma tha skasei logo ths first();
                        Url = UrlElement.first().text();
                    }
                    else{
                        Url = UrlElement.text();
                    }
                    YahooLinks[UrlNumber-1] = Url;
                    //this.Print(Url, UrlNumber);
                    //System.out.println("Url "+UrlNumber+": "+Url);
                    UrlNumber++;
                }
            }
            Elements next = doc.select("div[class=compPagination]");
            //for (Element link2 : next) {
            Elements titles2 = next.select("a[href]");
            String title2 = titles2.last().outerHtml();
            String segment1[] = title2.split("https://");
            String segment2[] = segment1[1].split("\"");
            UrlSearch = segment2[0];
            //System.out.println("NextPage: "+UrlSearch);
            //}
        }
        catch (IOException e) {
            System.out.println("Page can not found");
        }
       }while(UrlNumber < 100);
        return YahooLinks;
    }
    
}
