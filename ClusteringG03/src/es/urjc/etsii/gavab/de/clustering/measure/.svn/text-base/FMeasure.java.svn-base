package es.urjc.etsii.gavab.de.clustering.measure;

import java.io.File;
import java.util.List;
import es.urjc.etsii.gavab.de.clustering.algoritmo.NewsCluster;

public class FMeasure {

	// retorna el n�mero de documentos de la clase que se pasa que han sido
	// asignados
	// al cluster que tambi�n se pasa como par�metro
	private static int asignedDocumentsNumber(NewsCluster cluster,
			File classFile) {
		int countAsignedDocuments = 0;

		String[] classDocuments = classFile.list();
		for (String s : classDocuments) {
			if (cluster.contains(s))
				countAsignedDocuments++;
		}

		return countAsignedDocuments;
	}

	private static File[] getClasses(File clusterSolution) {
		if (clusterSolution.exists() && clusterSolution.isDirectory())
			return clusterSolution.listFiles();
		return null;
	}

	/**
	 * Obtiene el valor de la medidaF para el resultado de un proceso de
	 * clustering.
	 * 
	 * @param clusterSolution
	 *            Directorio que contiene la soluci�n �ptima (con la que hay que
	 *            comparar el resultado del clustering)
	 * @param clusterList
	 *            Lista con todos los clusters obtenidos
	 * @param documentsCollectionNumber
	 *            N�mero de documentos de la colecci�n que se ha agrupado
	 * @return
	 */
	public static float fMeasure(File clusterSolution,
			List<NewsCluster> clusterList, int documentsCollectionNumber) {

		float rcs; // R(Cr,Si), que es nri / nr
		float pcs; // P(Cr,Si), que es nri / ni
		float fcs; // F(Cr,Si) , la medida FScore para cada clase y cluster
		float fc; // la medida FScore de una clase ...
		// ... (el m�ximo de F(Cr,Si) de cada clase)
		float fScore = 0; // la medida FScore final obtenida

		File[] classes = FMeasure.getClasses(clusterSolution);
		if (classes != null) {
			for (int i = 0; i < classes.length; i++) {
				int documentClassNumber = classes[i].list().length;
				fc = 0;
				for (NewsCluster c : clusterList) {
					if (documentClassNumber != 0)
						rcs = (float) FMeasure.asignedDocumentsNumber(c,
								classes[i])
								/ (float) documentClassNumber;
					else
						rcs = 0;

					int documentClusterNumber = c.getSize();
					if (documentClusterNumber != 0)
						pcs = (float) FMeasure.asignedDocumentsNumber(c,
								classes[i])
								/ (float) documentClusterNumber;
					else
						pcs = 0;

					if (rcs + pcs == 0) // evitas dividir entre 0
						fcs = 0;
					else
						fcs = (2 * rcs * pcs) / (rcs + pcs);

					// hay que quedarse con el m�ximo
					fc = new Float(Math.max(fc, fcs));
				}
				if (documentsCollectionNumber != 0)
					fScore = fScore
							+ fc
							* ((float) documentClassNumber / (float) documentsCollectionNumber);

			}
		}

		return fScore;
	}
}
