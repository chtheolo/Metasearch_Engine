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

public class Bing {

    public String[] main(String UrlSearch2) {
        
        int UrlNumber = 1;
        String UrlSearch1 = "www.bing.com/search?q=";
        //String UrlSearch2 = "space";
        String UrlSearch =UrlSearch1+UrlSearch2;
        String[] BingLinks = new String[100];
        
        do{
    
        Document doc;
        try{
            
            doc = Jsoup.connect("http://"+UrlSearch).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
            Elements Contents = doc.select("ol[id=b_results]"); //Se ayto to shmeio ths HTML vriskonte ta epimerous Url pou theloume
        
            Elements GetUrlArea = Contents.select("li");
            for ( Element GetUrlElement : GetUrlArea) { 
      
                if(GetUrlElement.parent() == GetUrlArea.first().parent()){ //Elegxos gia na pairnw mono to Kyrio Url kai oxi ta "Ypo-Url"
                    Elements UrlElement = GetUrlElement.select("cite");
                    String Url = " ";
                    if(UrlElement.hasText() ){ //Elegxos an yparxei text giati an den yparxei to programma tha skasei logo ths first();
                        if(UrlElement.first().text() != " " && UrlNumber <= 100){
                            Url = UrlElement.first().text();
                            
                            if(Url.contains("https")){
                                Url = Url.substring(8);
                            }else if(Url.contains("http")){
                                Url = Url.substring(7);
                            }
                            BingLinks[UrlNumber-1] = Url;
                            //System.out.println("Url "+UrlNumber+": "+Url);
                            UrlNumber++;
                        }
                        
                    }
                    /*else{
                        Url = UrlElement.text();
                        
                    }*/

                }
            }
            //Elements next = doc.select("nav[role=navigation]");
            Elements next = doc.select("div[id=\"b_content\"]");
            //for (Element link2 : next) {
            Elements titles2 = next.select("a[href]");
            String title2 = titles2.last().outerHtml();
            String segment1[] = title2.split("href=\"");
            String segment2[] = segment1[1].split("\"");
            UrlSearch ="www.bing.com" +segment2[0];
            //System.out.println("NextPage: "+UrlSearch);
            //}
        }
        catch (IOException e) {
            System.out.println("Page can not found");
        }
        }while(UrlNumber < 100);
        return BingLinks;
    }
    
}

