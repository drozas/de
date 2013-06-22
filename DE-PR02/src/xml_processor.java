import java.io.*;

public class xml_processor {

	public static void getXMLValue(String linea, String etq1, String etq2, String title)
	{
		if(linea.contains(etq1))
		{
			String [] trozos = linea.split(etq1);
			for (int i= 0; i<trozos.length; i++)
			{
				String [] trozos2 = trozos[i].split(etq2);
				for (int j= 0; j<trozos2.length; j++)
				{
					if (!trozos2[j].equals("\t\t"))
						System.out.println(title + " : " + trozos2[j]);
				}
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File ("tenis.xml");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while((linea=br.readLine())!=null)
			{
				if(linea.contains("<torneo>"))
				{
					System.out.println("--------------- Torneo ---------------");
				}
				
				getXMLValue(linea, "<categoria>", "</categoria>", "Categoria");
				getXMLValue(linea, "<nombre>", "</nombre>", "Nombre");
				getXMLValue(linea, "<tipo_superficie>", "</tipo_superficie>", "Tipo de superficie");
				getXMLValue(linea, "<lugar>", "</lugar>", "Lugar");
				getXMLValue(linea, "<mes>", "</mes>", "Mes");
				getXMLValue(linea, "<anyo>", "</anyo>", "Año");

			}

		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta 
			// una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}


	}

}
