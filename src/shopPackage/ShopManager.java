package shopPackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import npcPackage.NPCManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ShopManager
{
	
JSONArray Names;
JSONArray Adjectives;
JSONArray Nouns;
JSONArray shopType;
JSONArray shopCondition;
JSONArray shopTraffic;
JSONArray shopTidbit;

JSONArray Inns;
JSONArray Booksellers;
JSONArray Blacksmiths;
JSONArray Bowyer;
JSONArray Leatherworker;
JSONArray Temple;
JSONArray Tailor;
JSONArray Potions;
JSONArray Magicshops;
JSONArray Jewelers;
JSONArray Generalstores;

JSONArray foodArray;

static NPCManager npcManager;

Random randomizer = new Random();


public ShopManager(NPCManager npcManager)
{
	this.npcManager = npcManager;
}

static ItemManager itemManager = new ItemManager();

//Generate Shop
public String generateShop(ShopTypeEnum shopType, WealthEnum wealth)
{	
	String name = generateName(shopType);
	String inventory = itemManager.displayInventory(shopType, wealth);
	
	String output = name+"\n"+"\n"+inventory;
	
	return generateName(shopType)
			+"\n"+generateTraits()
			+"\n"+itemManager.displayInventory(shopType, wealth);
}

//Generates Name
public String generateName(ShopTypeEnum shopType)
{
	String output = "";
	
	int randomNum = randomizer.nextInt(7);

	//Determines the format
	switch(randomNum)
	{
	case 0: //Name's Shop
		output = (npcManager.getAnyName()+"'s "+getShop(shopType));
		break;
	case 1: //Name & Name's Shop
		output = (npcManager.getAnyName() + " & " + npcManager.getAnyName()+"'s "+getShop(shopType));
		break;
	case 2: //The Adjective Noun
		output = ("The "+generate(Adjectives) + " " + npcManager.getAnyName()+" "+getShop(shopType));
		break;
	case 3: //The Noun & Noun
		output = ("The "+generate(Nouns) + " & " + generate(Nouns)+" "+getShop(shopType));
		break;
	case 4: //The Noun's
		output = ("The "+generate(Nouns)+" "+getShop(shopType));
		break;
	case 5: //Name's Adjective Noun
		output = (npcManager.getAnyName()+"'s "+generate(Adjectives)+" "+generate(Nouns)+" "+getShop(shopType));
		break;
	case 6: //Adjective Name's
		output = (generate(Adjectives)+" "+npcManager.getAnyName()+"'s "+getShop(shopType));
		break;
	case 7: //The Noun of Nouns
		output = "The "+(generate(Nouns)+" of "+generate(Nouns)+"s "+getShop(shopType));
		break;
	}
	return output;
}

//Generates traits
public String generateTraits()
{
	return (generate(shopCondition)
		+".\n"+generate(shopTraffic)
		+".\n"+generate(shopTidbit));
}

//Take in a JSONArray and return a random element
private String generate(JSONArray array)
{
	 return (String) array.get(randomizer.nextInt(array.size()));
}

private String getShop(ShopTypeEnum shopType)
{
	String output = "";
	
	switch(shopType)
		{
		case INN:
		output = (String) Inns.get(randomizer.nextInt(Inns.size()));
		break;
		case BLACKSMITH:
		output = (String) Blacksmiths.get(randomizer.nextInt(Blacksmiths.size()));
		break;
		case BOWYER:
		output = (String) Bowyer.get(randomizer.nextInt(Bowyer.size()));
		break;
		case LEATHERWORKER:
		output = (String) Leatherworker.get(randomizer.nextInt(Leatherworker.size()));
		break;
		case TEMPLE:
		output = (String) Temple.get(randomizer.nextInt(Temple.size()));
		break;
		case TAILOR:
		output = (String) Tailor.get(randomizer.nextInt(Tailor.size()));
		break;
		case POTIONS:
		output = (String) Potions.get(randomizer.nextInt(Potions.size()));
		break;
		case MAGICSHOP:
		output = (String) Magicshops.get(randomizer.nextInt(Magicshops.size()));
		break;
		case JEWELER:
		output = (String) Jewelers.get(randomizer.nextInt(Jewelers.size()));
		break;
		case GENERALSTORE:
		output = (String) Generalstores.get(randomizer.nextInt(Generalstores.size()));
		break;
		case BOOKSELLER:
		output = (String) Booksellers.get(randomizer.nextInt(Booksellers.size()));
		break;
		}
	
	return output;
}

public String getMagicItem(String type, String rarity)
{
	Item item = itemManager.generateSingleMagicItem(type, rarity);
	String output = "";
		
	if(item.getRarity() != null && item.getRarity().equals("Artifact"))
		output = item.getName()+", Priceless Artifact";
	else 
		output = item.getName()+", "+itemManager.determineSale(item);

	return output;
}

//JSON parser
public void parseJSON() throws FileNotFoundException, IOException, ParseException 
{
	// Parse Items First
	itemManager.parseJSON();

	//Get the JSON file as one JObject
    JSONParser parser = new JSONParser();
    
    try
    {
    JSONObject nameData = (JSONObject) parser.parse(new FileReader("data/ShopNames.json"));
    JSONObject shopData = (JSONObject) parser.parse(new FileReader("data/ShopTraits.json"));

    //Grab the Arrays from the JObject as JArrays
    Names  = (JSONArray) nameData.get("Names");
    Adjectives = (JSONArray) nameData.get("Adjectives");
    Nouns = (JSONArray) nameData.get("Nouns");
    Inns = (JSONArray) nameData.get("Inns");
    Blacksmiths = (JSONArray) nameData.get("Blacksmiths");
    Bowyer = (JSONArray) nameData.get("Bowyer");
    Leatherworker = (JSONArray) nameData.get("Leatherworker");
    Temple = (JSONArray) nameData.get("Temple");
    Tailor= (JSONArray) nameData.get("Tailor");
    Potions = (JSONArray) nameData.get("Potions");
    Magicshops = (JSONArray) nameData.get("Magicshops");
    Jewelers = (JSONArray) nameData.get("Jewelers");
    Generalstores = (JSONArray) nameData.get("Generalstores");
    Booksellers = (JSONArray) nameData.get("Booksellers");
    
    shopType = (JSONArray) shopData.get("Type");
    shopCondition = (JSONArray) shopData.get("Condition");
    shopTraffic = (JSONArray) shopData.get("Busy");
    shopTidbit = (JSONArray) shopData.get("Tidbit");

    }
    catch(FileNotFoundException e)
    {
        JOptionPane.showMessageDialog(null, e.toString(), "File Not Found Exception. Please place Names.txt in the same directory as the executable JAR file.",
                JOptionPane.ERROR_MESSAGE);
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null, e.toString(), "Error",
                JOptionPane.ERROR_MESSAGE);
    }
	}
}
