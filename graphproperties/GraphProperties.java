package graphproperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Reads graph from file in matrices format and determines properties of the graph
 *
 * @author Gavin Christie
 */
public class GraphProperties {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GraphProperties gp = new GraphProperties();
        Scanner file;
        /* Try and open the file */
        try {
            file = new Scanner(new FileInputStream("ass4-graphs.txt"));
        } catch (FileNotFoundException ex) {
            return;
        }
        int graphSize = 0, graphCount = 1;
        int[][] graph;

        while (file.hasNextInt()) {
            /* Get the size of the current graph */
            try {
                /* Get the size of the graph */
                graphSize = file.nextInt();
                file.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Problem happened, closing program.");
                System.exit(0);
            }

            /* Create the 2D array */
            graph = new int[graphSize][graphSize];

            for (int i = 0; i < graphSize; i++) {
                /* Split the string at spaces and convert the numbers to integers */
                String next = file.nextLine();
                StringTokenizer convert = new StringTokenizer(next, " ");
                int j = 0;
                while (convert.hasMoreTokens()) {
                    graph[i][j] = Integer.parseInt(convert.nextToken());
                    j++;
                }
            }
            
            /* Output the information for the current graph */
            String output = "Graph " + graphCount + ": " +
                    gp.determineGraphProperties(graph, graphSize);
            System.out.println(output);
            graphCount++;
        }

    }

    public String determineGraphProperties(int[][] graph, int size) {
        String properties = new String();
        int numOddDegreeVertices = 0, pendantVertices = 0, largestDegree = 0, 
                totalDegree = 0, numEdges = 0;
        
        /* For every vertex */
        for (int i = 0; i < size; i++) {
            int currentVertexDegree = 0;
            /* Check if there is an edge to one of the other vertices */
            for (int j = 0; j < size; j++) {
                if (graph[i][j] == 1) {
                    currentVertexDegree++;
                }
            }
            /* Add the degree to the total */
            totalDegree += currentVertexDegree;
            if ((currentVertexDegree % 2) == 1) {
                /* If the degree is odd add one to the number of odd degrees */
                numOddDegreeVertices++;
                if (currentVertexDegree == 1) {
                    /* If the degree is one add one to the number of pendant vertices */
                    pendantVertices++;
                }
            }
            /* If current vertex has the largest degree set the temp variable */
            if (largestDegree < currentVertexDegree) {
                largestDegree = currentVertexDegree;
            }
        }
        
        /* Get the number of edges by dividing totalDegree by 2 */
        numEdges = totalDegree / 2;
        properties = numEdges + " edges, " + pendantVertices + 
                " pendant vertices, largest degree " + largestDegree;
        
        /* Check if there is an euler path if there is no or two odd degree vertices */
        if (numOddDegreeVertices == 0 || numOddDegreeVertices == 2) {
            properties += ", HAS Euler path.";
        } else {
            properties += ", NO Euler path.";
        }

        /* Return the properties of the graph as a string */
        return properties;
    }
}

/** Output for provided graphs
 * Graph 1: 1002218 edges, 0 pendant vertices, largest degree 1089, NO Euler path.
 * Graph 2: 251108 edges, 0 pendant vertices, largest degree 560, NO Euler path.
 * Graph 3: 2757 edges, 0 pendant vertices, largest degree 8, NO Euler path.
 * Graph 4: 2626 edges, 0 pendant vertices, largest degree 63, NO Euler path.
 * Graph 5: 52825 edges, 0 pendant vertices, largest degree 266, HAS Euler path.
 * Graph 6: 8 edges, 2 pendant vertices, largest degree 2, HAS Euler path.
 */
