package application;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import java.io.File;
import java.io.IOException; 
import java.io.FileWriter;

import javafx.beans.binding.Binding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.GregorianCalendar;

public class Controller implements Initializable {
	public String collectInfo;
	public String dataType = "";
	public String delimiter;
	public String fileName;
	public int fieldNumber;
	public static String emptyField = "(empty)";
	public static boolean visiblNumField;
	@FXML
	CheckBox checkBoxRandomize = new CheckBox(); 
	
	@FXML
	Label progressLabel = new Label();
	@FXML
	Label labelAmount = new Label();
	@FXML
	Label delimiterLabel = new Label();
	
	@FXML
	ComboBox<String> delimiterCombo;
	public String[] delimiterOptions = {"Comma","Space", "Tab", "Custom"};
	
	@FXML 
	RadioButton dataRandomized = new RadioButton();
	@FXML
	RadioButton dataConsecutive = new RadioButton();
	@FXML
	RadioButton dataSame = new RadioButton();
	
	// I don't think I need to use these ***
	// @FXML
	// RadioButton patternDefault = new RadioButton();
	// @FXML
	// RadioButton patternRandomize = new RadioButton();
	
	@FXML
	ProgressBar progressBar = new ProgressBar();
	BigDecimal progress = new BigDecimal(String.format("%.2f", 0.0));
	
	@FXML
	TextField fileNameField = new TextField();
	@FXML
	TextField delimiterField = new TextField();
	@FXML 
	TextField numFields = new TextField();
	
	@FXML
	MenuItem fileClose = new MenuItem();
	MenuItem aboutMe = new MenuItem();
	
	@FXML
	Slider slider = new Slider();
	public int dataAmount;
	
	
	public static int returnDateCount;  
	public static int createRandomWordCount;
	public static int createRandomIPCount;
	public static int returnIntegerCount;
	public static int returnRandDoubleCount;
	public static int returnURLCount;
	public static int emptyFieldCount;
	public static int portNumberCount;
	public static int returnBoolCount;
	public static int returnVectorCount;
	
	public void closeOut() {
		System.exit(0);
	}
	
	public static String seqString(int i) {
        return i < 0 ? "" : seqString((i / 26) - 1) + (char)(65 + i % 26);
    }
	
	public void updateDelimiter() {
		delimiter = delimiterField.getText();
	}

	public void showMessage(String message, boolean exits) {
		Stage popupwindow=new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Data Generator 2022");   
		Label label1= new Label(message);   
		Button button1= new Button("Close");
		button1.setOnAction(e -> popupwindow.close());
		VBox layout= new VBox(10);   
		layout.getChildren().addAll(label1, button1); 
		layout.setAlignment(Pos.CENTER);  
		Scene scene1= new Scene(layout, 300, 250); 
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();
		if (exits==true) {
			System.exit(0);
		}
	}
	
	public static String createRandomWord(int len) {  // Convert to serial integer
        String name = "";
        for (int i = 0; i < len; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            name += c;
        }   return name;   
    }
    
    public static String createRandomIP(int length) {
        Random r = new Random();
        return r.nextInt(length) + "." + r.nextInt(length) + "." + r.nextInt(length) + "." + r.nextInt(length);
    }
    
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
    public static String returnDate() {
    	String theDate;
    	GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        theDate = ((gc.get(gc.MONTH)+1) + "-" + (gc.get(gc.DAY_OF_MONTH)) + "-" + gc.get(gc.YEAR));
        return theDate;
    }
    
    public static String returnInteger(int portMax) {
    	int portNum;
    	Random randPort = new Random();
    	portNum = randPort.nextInt(portMax);
    	return (String.valueOf(portNum));
    }
    
    public static String returnRandDouble(double min, double max) {
    	double random = ThreadLocalRandom.current().nextDouble(min, max);
    	
    	String s = String.format("%.4f",random);
    	return s;
    }
    
    public String writeInfo() {
    	
    	dataAmount = Integer.valueOf(labelAmount.getText());
    	if (dataConsecutive.isSelected()){dataType = "Zeek Random Data"; 	}
    	if (dataRandomized.isSelected()) {dataType = "Randomized Data";    	}
    	if(dataSame.isSelected()) {dataType = "Zeek Default Data";    	}
    	collectInfo = 
    			"\n" +
    			"# Lines of Data  : " + labelAmount.getText() + "\n" +
    		    "# Fields in Each : " + Integer.valueOf(numFields.getText()) + "\n" +
    			"# Delimiter      : " + delimiterCombo.getValue() + "\n" +
    			"# Data Category  : " + dataType + "\n" +
    			"# Filename       : " + fileNameField.getText() + "\n"  ;
    	
    	return collectInfo;
    }
    
    public static String enumReturn(int enumType) {
    	switch(enumType) {
    		case 1:
    			return "udp";
    		case 2: 
    			return "tcp";
 
    	}
    	return "udp";
    }
    
    public static String returnBool() {
    	int x;
    	Random rand = new Random();
		x = rand.nextInt(2)+1;
		if (x==1) {return "true";}
		else {return "false";}
    }
    
    public static String returnVector(int vecLength) {
    	String combined = null;
    	
    	for (int i=0; i < vecLength-1; i++) {
    		combined += returnRandDouble(0.1,5.0)+",";
    	}
    	combined += returnRandDouble(0.1,5.0);
    	
    	return combined;
    }
    
    
    public static String zeekDataFormat(String dataType) {
    	switch (dataType) {
			case "time":
				returnDateCount+=1;
				return 	returnDate().toString();
			case "string":
				createRandomWordCount+=1;
				return createRandomWord(12).toString();
			case "addr":
				createRandomIPCount+=1;
				return createRandomIP(250).toString();
			case "port":
				returnIntegerCount+=1;
				return returnInteger(65000).toString();
			case "interval":
				returnIntegerCount+=1;
				return returnInteger(999).toString();
			case "enumUDP":
				portNumberCount+=1;
				return enumReturn(1).toString();
			case "enumTCP":
				portNumberCount+=1;
				return enumReturn(2).toString();
			case "count":
				returnIntegerCount+=1;
				return returnInteger(20000).toString();
			case "boolean":
				returnBoolCount+=1;
				return returnBool().toString();
			case "vector":
				returnVectorCount+=1;
				return returnVector(3).toString();
			case "url":
				returnURLCount+=1;
				return returnURL().toString();
			case "empty":
				emptyFieldCount+=1;
				return emptyField;
				
			default:
				return "OK";
    	}
    }
    
    public void createFile () throws InterruptedException, IOException {
    	if (!dataRandomized.isSelected() && !dataConsecutive.isSelected() && !dataSame.isSelected())
		{
			showMessage("You must choose radio buttons", false);
			return;
		}
    	
		String delimiterTemp;
		int progressAmount;
		int switchRandom;
		dataAmount = Integer.valueOf(labelAmount.getText());
		
		if (numFields.getText()!="") {
			fieldNumber = Integer.valueOf(numFields.getText());
			}
		else {
			showMessage("# of Fields must have a number",false);
			return;
		}
		
		delimiterTemp = String.valueOf(delimiterCombo.getValue());
		System.out.println(delimiterTemp);
		
		if (delimiterTemp == null) {
			delimiterTemp="Custom";
		}
		switch (delimiterTemp) {
			case "Comma":
				delimiter = ",";
				break;
			case "Space":
				delimiter = " ";
				break;
			case "Tab":
				delimiter = "\t";
				break;
			case "Custom":
				delimiter = String.valueOf(delimiterField.getText());
				break;
			/*
			 * case "": delimiter = String.valueOf(delimiterField.getText()); break;
			 */
				
			default:
				showMessage("You must select a delimiter.",false);
				return;
		}
		
	    if (fileNameField.getText() == "") { 
		   showMessage("Filename empty.",false); 
		   return; 
		   }
		
		String getInfo = writeInfo();
		fileName = "/tmp/test/"+fileNameField.getText();
	
		
		
		
		try {

			FileWriter writeFile = new FileWriter(fileName);
			
			if(dataSame.isSelected()) {
				writeFile.write("#Fields	ts		uid	id.orig_h	id.orig_p	id.resp_h	id.resp_p	proto	trans_id	rtt		query		qclass		q		class_name	qtype	qtype_name	rcode	rcode_name	AA		TC		RD		RA		Z		answers			TTLs				rejected");
				writeFile.write("#Type		time	string			addr		port		addr		port	enum		count	interval	string		count	string		count	string		count	string		bool	bool	bool	bool	count	vector[string]	vector[interval]	bool");
			
			}
			
			
			// progressAmount = Integer.valueOf(dataAmount/100);
			writeFile.write(getInfo+"\n");
			for (int i=1; i<=dataAmount; i++) { 
				for (int fields = 1; fields <= fieldNumber; fields++) {

						if(dataSame.isSelected()) {
							writeFile.write(zeekDataFormat("time"));
							
							
	
							
							}
		
						
						
						if (dataConsecutive.isSelected()){
							Random rand = new Random();
							switchRandom = rand.nextInt(8);
							switch (switchRandom) {
							
								case 0:
									writeFile.write(returnDate());
									returnDateCount+=1;
									break;
									
								case 1: // some random word
									createRandomWordCount+=1;
									writeFile.write(createRandomWord(12));
									break;
									
								case 2: // some random IP
									createRandomIPCount+=1;
									writeFile.write(createRandomIP(250));
									break;
									
								case 3: // some random port
									returnIntegerCount+=1;
									writeFile.write(returnInteger(65000));
									break;
									
								case 4:  // some random double 
									returnRandDoubleCount+=1;
									writeFile.write(returnRandDouble(1111.1111,9999.9999));
									break;
									
								case 5: // some random url hostname
									returnURLCount+=1;
									writeFile.write(returnURL());
									break;
									
								case 6: // some random date
									returnDateCount+=1;
									writeFile.write(returnDate());
									break;
									
								case 7: // drop an empty now and then 
									emptyFieldCount+=1;
									writeFile.write(emptyField);
									break;
									
								default:
									break;
							}

							if (fields == fieldNumber) 
							 	{writeFile.write("");}
							else
								{writeFile.write(delimiter);}
							}
					
						
						if (dataRandomized.isSelected()) {
							Random rand = new Random();
							switchRandom = rand.nextInt(12);
							switch (switchRandom) {
							
								case 0:
									portNumberCount+=1;
									writeFile.write(enumReturn(1));
									break;
									
								case 1: // some random word
									createRandomWordCount+=1;
									writeFile.write(createRandomWord(12));
									break;
									
								case 2: // some random IP
									createRandomIPCount+=1;
									writeFile.write(createRandomIP(250));
									break;
									
								case 3: // some random port
									returnIntegerCount+=1;
									writeFile.write(returnInteger(65000));
									break;
									
								case 4:  // some random double 
									returnRandDoubleCount+=1;
									writeFile.write(returnRandDouble(1111.1111,9999.9999));
									break;
									
								case 5: // some random url hostname
									returnURLCount+=1;
									writeFile.write(returnURL());
									break;
									
								case 6: // some random date
									returnDateCount+=1;
									writeFile.write(returnDate());
									break;
									
								case 7: // drop an empty now and then 
									emptyFieldCount+=1;
									writeFile.write(emptyField);
									break;
								
								case 8: // drop an empty now and then 
									portNumberCount+=1;
									writeFile.write(enumReturn(1));
									break;
									
								case 9: // drop an empty now and then 
									portNumberCount+=1;
									writeFile.write(enumReturn(2));
									break;
									
								case 10:
									returnVectorCount+=1;
									writeFile.write(returnVector(3));
									break;
									
								case 11:
									writeFile.write(returnBool());
									returnBoolCount+=1;
									break;
									
								default:
									break;
							}

							if (fields == fieldNumber) 
							 	{writeFile.write("");}
							else
								{writeFile.write(delimiter);}			
							}
						// progressAmount = (i*(i%100));
						// progressBar.setProgress(progressAmount);
						// increaseProgress(progressAmount);	
				}
				writeFile.write("\n");
			}
			writeFile.close(); 
		}
		catch (IOException e) 
			{ 
			showMessage("An error occurred writing the file.", false);
			e.printStackTrace(); 
			return;
			}
		
		fileReport(fileName);
		
		
		showMessage("File Completed: " + fileName + "\n" + "Metadata File: " + fileName+".meta", true);
		}
	
    // @FXML
	/*
	 * private void isDelivery(ActionEvent event){
	 * numFields.setDisable(!dataSame.isArmed());
	 * delimiterCombo.setDisable(!dataSame.isArmed()); }
	 */
    
    
    public void disableFields() {
    	
    	numFields.setDisable(!dataSame.isArmed());
    	
    	// if (visiblNumField = false) {numFields.setDisable(true);}
    	// else if (visiblNumField = true) {numFields.setDisable(false);}
    	// boolean = !boolean;
    }
    
    public void enableFields() {
    	if (visiblNumField = true) {numFields.setDisable(false);}
    	
    }
	public void resetForm() {
		System.out.println("Reset");
	}
	
	public void updateSliderAmount(Number value) {
		value = value.intValue();
		labelAmount.setText(String.valueOf(value));
	}
	
	public void aboutPopUp() {
		Stage popupwindow=new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Data Generator 2022");   
		Label label1= new Label("Created by Wallaroo & Enlighten\nSelect type of Data\n");   
		Button button1= new Button("Close");
		button1.setOnAction(e -> popupwindow.close());
		VBox layout= new VBox(10);   
		layout.getChildren().addAll(label1, button1); 
		layout.setAlignment(Pos.CENTER);  
		Scene scene1= new Scene(layout, 300, 250); 
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();
	}
	
	public void increaseProgress (double progress) {
		progressBar.setProgress(progress);
	}
	
	public void fileReport(String fileName) throws IOException {
		FileWriter writeMetaFile = new FileWriter(fileName+".meta");

		writeMetaFile.write("Metadata Report: \n");
		writeMetaFile.write("createRandomWordCount :"+createRandomWordCount+"\n");
		writeMetaFile.write("createRandomIPCount   :"+createRandomIPCount+"\n");
		writeMetaFile.write("returnIntegerCount    :"+returnIntegerCount+"\n");
		writeMetaFile.write("returnRandDoubleCount :"+returnRandDoubleCount+"\n");
		writeMetaFile.write("returnURLCount        :"+returnURLCount+"\n");
		writeMetaFile.write("emptyFieldCount       :"+emptyFieldCount+"\n");
		writeMetaFile.write("portNumberCount       :"+portNumberCount+"\n");
		writeMetaFile.write("returnBoolCount       :"+returnBoolCount+"\n");
		writeMetaFile.write("returnVectorCount     :"+returnVectorCount+"\n");
		writeMetaFile.write(collectInfo);
		
		writeMetaFile.close(); 
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < delimiterOptions.length; i++) {
			delimiterCombo.getItems().add((delimiterOptions[i]));
		}
		// delimiterField.setText(",");
		updateDelimiter();
		Random rand = new Random();
		int upperbound = 3999;
		int fileIncrement = rand.nextInt(upperbound)+1;
		fileNameField.setText("f-"+fileIncrement+".txt");
		int min = 1;
		int max = 1_000_000; // _000;
		slider.setMin(min);
        slider.setMax(max);
        slider.setValue(100);
        labelAmount.setText(String.valueOf(1));
        //slider.setShowTickLabels(true);
        //slider.setShowTickMarks(true);
        //slider.setBlockIncrement(40000); // Integer.valueOf((int) (max/(max*.25))));
        
		// progressBar.setStyle("-fx-accent: #9966CC");
        
		slider.valueProperty().addListener(
	           new ChangeListener<Number>() {
	 
		            public void changed(ObservableValue <? extends Number >
		                      observable, Number oldValue, Number newValue)
		            {
		            	// labelAmount.setText("value: " + newValue);
		            	// updateSliderAmount(newValue);
		            	labelAmount.setText(String.valueOf(newValue.intValue()));
		            }
	        });
		
		
	}
	
	public static String returnURL() {
    	String[] urlList = {
    			"blogger.com","cdc.gov","steampowered.com","clickbank.net","a8.net","tools.google.com","indiatimes.com","leparisien.fr","theatlantic.com","thestar.com",
    			"google.com","independent.co.uk","alicdn.com","hollywoodreporter.com","calameo.com","developers.google.com","huffpost.com","instagram.com","pl.wikipedia.org","goal.com",
    			"youtube.com","afternic.com","soundcloud.com","biglobe.ne.jp","thehill.com","brandbucket.com","goo.gl","doi.org","bp1.blogger.com","medicalnewstoday.com",
    			"support.google.com","pixabay.com","sciencemag.org","rtve.es","behance.net","imdb.com","office.com","chicagotribune.com","tiktok.com","indianexpress.com",
    			"microsoft.com","wsj.com","instructables.com","nationalgeographic.com","scoop.it","youronlinechoices.com","opera.com","akamaihd.net","skype.com","asus.com",
    			"play.google.com","twitter.com","20minutos.es","prezi.com","searchenginejournal.com","creativecommons.org","msn.com","bloglovin.com","zendesk.com","slate.com",
    			"apple.com","books.google.com","pbs.org","gmail.com","123rf.com","wikimedia.org","change.org","urbandictionary.com","nginx.org","ebay.de",
    			"linkedin.com","abril.com.br","bitly.com","nikkei.com","ndtv.com","dailymotion.com","myaccount.google.com","freepik.com","welt.de","techradar.com",
    			"en.wikipedia.org","draft.blogger.com","abcnews.go.com","springer.com","udemy.com","google.es","mediafire.com","plos.org","wix.com","investopedia.com",
    			"cloudflare.com","rakuten.co.jp","yadi.sk","thenai.org","so-net.ne.jp","files.wordpress.com","washingtonpost.com","google.nl","eventbrite.com","fifa.com",
    			"docs.google.com","express.co.uk","pinterest.fr","repubblica.it","greenpeace.org","google.de","cpanel.com","as.com","wikihow.com","mystrikingly.com",
    			"wordpress.org","buydomains.com","groups.google.com","cnil.fr","eonline.com","live.com","ytimg.com","whitehouse.gov","stanford.edu","merriam-webster.com",
    			"youtu.be","ipv4.google.com","webmd.com","cambridge.org","asahi.com","line.me","wikia.com","rapidshare.com","cbsnews.com","pinterest.co.uk",
    			"maps.google.com","booking.com","over-blog.com","ea.com","ebay.co.uk","weebly.com","shutterstock.com","kickstarter.com","twitch.tv","imgur.com",
    			"mozilla.org","amazon.co.jp","clarin.com","ikea.com","iubenda.com","yahoo.com","dailymail.co.uk","samsung.com","gnu.org","wn.com",
    			"accounts.google.com","id.wikipedia.org","ovh.com","qq.com","inc.com","nih.gov","researchgate.net","npr.org","gov.br","sagepub.com",
    			"bp.blogspot.com","amazon.es","outlook.com","tripadvisor.com","legifrance.gouv.fr","globo.com","google.it","ja.wikipedia.org","academia.edu","channel4.com",
    			"drive.google.com","archive.org","it.wikipedia.org","rt.com","nl.wikipedia.org","paypal.com","fandom.com","akamaized.net","ca.gov","rambler.ru",
    			"whatsapp.com","wp.com","vice.com","oracle.com","dribbble.com","bbc.co.uk","list-manage.com","mashable.com","francetvinfo.fr","sina.com.cn",
    			"adobe.com","hugedomains.com","canva.com","discord.gg","unam.mx","enable-javascript.com","time.com","yahoo.co.jp","surveymonkey.com","zeit.de",
    			"sites.google.com","terra.com.br","sapo.pt","news.com.au","adweek.com","who.int","un.org","gooyaabitemplates.com","sputniknews.com","fortune.com",
    			"googleusercontent.com","marketingplatform.google....","unesco.org","lycos.com","wiktionary.org","policies.google.com","news.yahoo.com","britannica.com","ria.ru","mercurynews.com",
    			"europa.eu","mirror.co.uk","netlify.app","thetimes.co.uk","faz.net","theguardian.com","bit.ly","abc.es","newyorker.com","sakura.ne.jp",
    			"plus.google.com","plesk.com","e-recht24.de","disqus.com","corriere.it","w3.org","telegram.me","buzzfeed.com","insider.com","groups.yahoo.com",
    			"github.com","namecheap.com","playstation.com","bing.com","focus.de","mail.google.com","aboutads.info","techcrunch.com","theverge.com","utexas.edu",
    			"t.me","forms.gle","google.co.in","search.yahoo.com","steamcommunity.com","jimdofree.com","netvibes.com","alexa.com","wiley.com","airbnb.com",
    			"es.wikipedia.org","estadao.com.br","bp2.blogger.com","php.net","about.me","cpanel.net","nasa.gov","ft.com","harvard.edu","vkontakte.ru",
    			"istockphoto.com","dan.com","lavanguardia.com","washington.edu","google.com.au","google.co.jp","disney.com","discord.com","sciencedaily.com","pcmag.com",
    			"vk.com","huffingtonpost.com","finance.yahoo.com","standard.co.uk","example.com","cnn.com","amzn.to","zoom.us","about.com","redhat.com",
    			"vimeo.com","android.com","ted.com","photobucket.com","digitaltrends.com","ok.ru","offset.com","ebay.com","guardian.co.uk","twimg.com",
    			"feedburner.com","nature.com","hm.com","adssettings.google.com","sky.com","fb.com","aol.com","photos1.blogger.com","nginx.com","ovh.co.uk",
    			"facebook.com","scribd.com","berkeley.edu","cnbc.com","blackberry.com","t.co","ibm.com","gofundme.com","bild.de","livejournal.com",
    			"uol.com.br","amazon.fr","mozilla.com","storage.googleapis.com","xing.com","google.pl","canada.ca","secureserver.net","googleblog.com","nokia.com",
    			"amazon.com","usatoday.com","google.com.tw","netflix.com","lenta.ru","wired.com","engadget.com","cornell.edu","cbc.ca","salon.com",
    			"search.google.com","ig.com.br","nypost.com","godaddy.com","yandex.com","picasaweb.google.com","newsweek.com","nydailynews.com","spotify.com","worldbank.org",
    			"forbes.com","issuu.com","e-monsite.com","yelp.com","zdnet.com","de.wikipedia.org","ovh.net","hp.com","amazon.it","debian.org",
    			"gravatar.com","networkadvertising.org","weibo.com","themeforest.net","google.co.id","shopify.com","economist.com","ietf.org","sfgate.com","dictionary.com",
    			"news.google.com","businessinsider.com","calendar.google.com","picasa.google.com","kompas.com","pinterest.com","apache.org","oup.com","sedo.com","ucoz.ru",
    			"bbc.com","4shared.com","gizmodo.com","zdf.de","bbci.co.uk","telegraph.co.uk","interia.pl","timeweb.ru","ziddu.com","hbr.org",
    			"dropbox.com","amazon.co.uk","quora.com","lefigaro.fr","answers.com","hatena.ne.jp","sciencedirect.com","tmz.com","xbox.com","archives.gov",
    			"myspace.com","foxnews.com","code.google.com","mega.nz","kotaku.com","mail.ru","google.ca","detik.com","wikipedia.org","billboard.com",
    			"slideshare.net","google.ru","feedproxy.google.com","alibaba.com","usgs.gov","thesun.co.uk","mit.edu","t-online.de","icann.org","usda.gov",
    			"gstatic.com","tinyurl.com","addtoany.com","xinhuanet.com","coursera.org","google.co.uk","lemonde.fr","m.wikipedia.org","variety.com","etsy.com",
    			"nytimes.com","aliexpress.com","pexels.com","arxiv.org","si.edu","translate.google.com","psychologytoday.com","liberation.fr","dell.com","thoughtco.com",
    			"google.com.br","photos.google.com","dw.com","sendspace.com","impress.co.jp","elpais.com","dailystar.co.uk","bandcamp.com","house.gov","public-api.wordpress.com",
    			"reuters.com","cnet.com","yandex.ru","deezer.com","statista.com","ru.wikipedia.org","huawei.com","walmart.com","weforum.org","hindustantimes.com",
    			"fr.wikipedia.org","amazon.de","ggpht.com","goodreads.com","politico.com","google.fr","naver.com","ign.com","lifehacker.com","namesilo.com",
    			"pt.wikipedia.org","bloomberg.com","addthis.com","nbcnews.com","amazon.ca","elmundo.es","smh.com.au","abc.net.au","fda.gov","redbull.com",
    			"medium.com","wa.me","privacyshield.gov","target.com","venturebeat.com","get.google.com","doubleclick.net","dreamstime.com","dreniq.com","mysql.com",
    			"gov.uk","espn.com","spiegel.de","rollingstone.com","fb.me","latimes.com","imageshack.us","metro.co.uk","uefa.com","cbslocal.com"
    			};
    			Random randURL=new Random();        
    	      	int randomNumber=randURL.nextInt(urlList.length);
    	      	return(urlList[randomNumber]);
    			
    	}
    

    }
	 


