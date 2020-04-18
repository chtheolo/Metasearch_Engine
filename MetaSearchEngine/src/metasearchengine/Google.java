package metasearchengine;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Google{

    //private  Document doc = null;
    //private static ArrayList<String> google  = new ArrayList<String>();
    private static int countGoogle = 0;
   
    public String[] main(String request) {
        Document doc;
        String link = new String("http://www.google.com/search?q="+request);
        String link2 = link;
        Integer Num = 10;
        String[] GoogleLinks = new String[100];
        do{
            try{
        
            String site = null;
            doc = Jsoup.connect(link2).userAgent("Mozilla").timeout(20000).get();
            for(Element result : doc.select("cite")){
                if(countGoogle < 100){
                    //if(result.text().contains("."))
                    if(result.text().contains("https")){
                        site = result.text().substring(8);
                        countGoogle++;
                    }else if(result.text().contains("http")){
                        site = result.text().substring(7);
                        countGoogle++;
                    }else{
                        site = result.text();
                        countGoogle++;
                    }
                    if(site.endsWith("/")){
                        site = site.substring(0, site.length()-1);   
                    }
                    /*
                    if(site.startsWith("www.")){
                        site = site.substring(4);
                    }*/
                    //google.add(site);
                    
                    GoogleLinks[countGoogle-1] = site;
		
                    //System.out.println("Url "+countGoogle+": "+site); 
                }
            }       
	}
        catch (IOException e) {
            // do something
            e.printStackTrace();
        }
            link2 = link + "&start="+Num;
            Num = Num + 10;

        }while(countGoogle < 100);
        return GoogleLinks;
         
    }   
}
