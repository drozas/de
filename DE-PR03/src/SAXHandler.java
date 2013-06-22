import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;


public class SAXHandler extends DefaultHandler 
{
	private int counter;
	private Document doc;
	private Element last = null;
	
	StringBuffer accumulator = new StringBuffer();
	String textAux = "";
	
	public SAXHandler(Document doc)
	{
		super();
		this.doc = doc;
		this.counter = 0;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
			//Cuando encontramos etiqueta de inicio DOC, creamos la raíz y agregamos la fecha como atributo
			if(localName.equalsIgnoreCase("DOC"))
			{
				for (int i=0; i<attributes.getLength(); i++)
				{
					if (attributes.getLocalName(i).equalsIgnoreCase("DATE"))
					{
						Element root = doc.createElement("DOC");
						root.setAttribute("DATE", attributes.getValue(i));
			            this.doc.appendChild(root);
			            this.last = root;
					}
				}
			}else if(localName.equalsIgnoreCase("TITLE"))
			{
				//Crear un nuevo nodo hijo y agregarle el contenido
				Element child = doc.createElement("TITLE");
				this.last.appendChild(child);
				this.last = child;
				
				//Limpiar buffer para evitar espacios en título
				this.accumulator.delete(0,this.accumulator.length());
				
			}else if(localName.equalsIgnoreCase("BODY"))
			{
				//Crear un nuevo nodo hijo y agregarselo a la raíz
				Element child = doc.createElement("BODY");
				this.last.getParentNode().appendChild(child);
				this.last = child;
			}else if(localName.equalsIgnoreCase("W") || localName.equalsIgnoreCase("MW"))
			{
				//Cuando empieza un nodo W o WR, acumulamos el contenido de su atributo FRM
				for (int i=0; i<attributes.getLength(); i++)
				{
					if (attributes.getLocalName(i).equalsIgnoreCase("FRM"))
					{
						//TODO: Buscar una forma más elegante
						//Acumular contenido y agregar espacio
						this.textAux += attributes.getValue(i);
						if ((attributes.getValue(i).equals(".")))
							this.textAux += "\n\t";
						else
							this.textAux+= " ";
					}
				}
			}
	}
	
	public void endElement(String uri, String localName, String qName)
	{
		//Si detectamos cierre de título, agregamos buffer y lo limpiamos
		if(localName.equalsIgnoreCase("TITLE"))
		{
			Text text = doc.createTextNode(this.accumulator.toString());
            this.last.appendChild(text);	
			this.accumulator.delete(0,this.accumulator.length());
			
		}else if(localName.equalsIgnoreCase("P"))
		{
			//Crear nodo de tipo párrafo y agregar su número como atributo y el buffer como texto
			//Será hijo de body (el último del que guardaremos su posición
			this.counter++;
			Element parrafo = doc.createElement("PARRAFO");
			parrafo.setAttribute("NUM", Integer.toString(this.getCounter()));
			parrafo.setTextContent(this.textAux);
			this.textAux = "";
			
			//Hacemos al nodo P hijo de body
            this.last.appendChild(parrafo);
			
		}

	}
	
	public void characters(char[] buffer, int start, int length) {
		    this.accumulator.append(buffer, start, length);
	}
	/**
	 * Devuelve el nº de párrafos
	 * 
	 * @return Nº de párrafos
	 */
	public int getCounter()
	{
		return this.counter;
	}
	
	/**
	 * 
	 * Devuelve instancia de documento dom que se crea en el parseo
	 * 
	 * @return	Documento dom generado
	 */
	public Document getDocument()
	{
		return this.doc;
	}

}
