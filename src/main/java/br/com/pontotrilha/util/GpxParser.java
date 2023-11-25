package br.com.pontotrilha.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GpxParser {

    private final String gpxContent;

    public GpxParser(String gpxContent) {
        this.gpxContent = gpxContent;
    }

    public void parseGpx() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Criar um InputStream a partir do conteúdo do arquivo GPX
            InputStream stream = new ByteArrayInputStream(gpxContent.getBytes("UTF-8"));

            Document doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();

            // Exemplo: obtendo pontos (waypoints) do GPX
            NodeList waypointList = doc.getElementsByTagName("wpt");

            for (int temp = 0; temp < waypointList.getLength(); temp++) {
                Node waypointNode = waypointList.item(temp);

                if (waypointNode.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println("Latitude: " + waypointNode.getAttributes().getNamedItem("lat").getNodeValue());
                    System.out.println("Longitude: " + waypointNode.getAttributes().getNamedItem("lon").getNodeValue());
                }
            }

            // Outras partes do GPX podem ser acessadas da mesma forma, como trilhas (tracks) e segmentos (segments)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        try {
            // Substitua "seuArquivo.gpx" pelo caminho do seu arquivo GPX
            String gpxContent = "<gpx>...</gpx>";

            GpxParser gpxParser = new GpxParser(gpxContent);
            gpxParser.parseGpx();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
