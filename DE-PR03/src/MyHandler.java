import java.util.ArrayList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;


public class MyHandler extends DefaultHandler 
{
	private int counter = 0;
	private ArrayList<String> listaLugares = new ArrayList<String>();
	private ArrayList<String> listaPersonas = new ArrayList<String>();
	private String aux = "";
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
			if(localName.equalsIgnoreCase("P"))
			{
				this.counter++;
			}else if(localName.equalsIgnoreCase("MW")){
				for (int i=0; i<attributes.getLength(); i++)
					if (attributes.getLocalName(i).equalsIgnoreCase("FRM"))
					{
						aux = attributes.getValue(i);
						//System.out.println("nodo MW detectado!" + attributes.getValue(i));
					}
			}else if(localName.equalsIgnoreCase("CAT")){
				for (int i=0; i<attributes.getLength(); i++)
					if (attributes.getLocalName(i).equalsIgnoreCase("CODE") && attributes.getValue(i).equalsIgnoreCase("LOCATION"))
					{
						//System.out.println("CAT CON LOCATION = " + aux);
						this.listaLugares.add(aux);
					}else if(attributes.getLocalName(i).equalsIgnoreCase("CODE") && attributes.getValue(i).equalsIgnoreCase("PERSON"))
					{
						//System.out.println("CAT CON PERSON = " + aux);	
						this.listaPersonas.add(aux);
					}
			}
			
	}

	public int getCounter()
	{
		return this.counter;
	}
	
	public ArrayList<String> getListaLugares()
	{
		return this.listaLugares;
	}
	
	public ArrayList<String> getListaPersonas()
	{
		return this.listaPersonas;
	}

}
