package vn.edu.iuh.fit.jdepend.parser;

import jdepend.framework.PackageFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import vn.edu.iuh.fit.jdepend.dto.ErrorPackage;
import vn.edu.iuh.fit.jdepend.dto.Package;
import vn.edu.iuh.fit.jdepend.dto.StatisticsField;
import vn.edu.iuh.fit.jdepend.dto.StatsPackage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class XMLParser {
    public List<Package> readXML(String path){
        List<Package> packages = new ArrayList<>();
        try {DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(path);
            Document document = builder.parse(file);

            Element jDependElement = document.getDocumentElement();
            NodeList packagesList = jDependElement.getElementsByTagName("Packages");

            for (int i = 0; i < packagesList.getLength(); i++) {
                Node packagesNode = packagesList.item(i);
                if (packagesNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element packagesElement = (Element) packagesNode;
                    NodeList packageList = packagesElement.getElementsByTagName("Package");
                    for (int j = 0; j < packageList.getLength(); j++) {
                        Node packageNode = packageList.item(j);
                        if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element packageElement = (Element) packageNode;
                            String packageName = packageElement.getAttribute("name");

                            Element error = (Element) packageElement.getElementsByTagName("error").item(0);
                            if(error!=null)
                                packages.add(new ErrorPackage(packageName,error.getTextContent()));
                            else {
                                Element statsElement = (Element) packageElement.getElementsByTagName("Stats").item(0);
                                if(statsElement==null) continue;
                                packages.add(new StatsPackage(packageName,
                                        Integer.parseInt(statsElement.getElementsByTagName("TotalClasses").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("ConcreteClasses").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("AbstractClasses").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("Ca").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("Ce").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("A").item(0).getTextContent()),
                                        Float.parseFloat(statsElement.getElementsByTagName("I").item(0).getTextContent()),
                                        Float.parseFloat(statsElement.getElementsByTagName("D").item(0).getTextContent()),
                                        Integer.parseInt(statsElement.getElementsByTagName("V").item(0).getTextContent())
                                        ));
                            }
                        }
                    }
                }
            }
            return packages;
        } catch (Exception e) {
            log.error("** "+e.getMessage());
            return packages;
        }
    }

    public List<StatisticsField> statisticsPie(List<Package> packages){
        AtomicInteger totalOke = new AtomicInteger();
        AtomicInteger totalNotOke = new AtomicInteger();
        List<StatisticsField> statisticsFields = new ArrayList<>();
        packages.forEach(aPackage -> {
            if(!(aPackage instanceof ErrorPackage)){
                StatsPackage statsPackage = (StatsPackage) aPackage;
                if(
                        statsPackage.getTotalClasses() >0
                        && statsPackage.getConcreteClasses() >0
                        && statsPackage.getCa()<= 20
                        && statsPackage.getCe()<= 20
                ) totalOke.getAndIncrement();
                else totalNotOke.getAndIncrement();
            }
        });
        statisticsFields.add(new StatisticsField("Package OKE", totalOke.get()));
        statisticsFields.add(new StatisticsField("Package NOT OKE", totalNotOke.get()));
        return statisticsFields;
    }

    public List<StatisticsField> statisticsBar(List<Package> packages){
        List<StatisticsField> statisticsFields = new ArrayList<>();
        packages.forEach(aPackage -> {
            if(!(aPackage instanceof ErrorPackage)){
                StatsPackage statsPackage = (StatsPackage) aPackage;
                statisticsFields.add(new StatisticsField(statsPackage.getName(), statsPackage.getCa()));
            }
        });
        return statisticsFields;
    }

    public List<StatisticsField> statisticsBarCe(List<Package> packages){
        List<StatisticsField> statisticsFields = new ArrayList<>();
        packages.forEach(aPackage -> {
            if(!(aPackage instanceof ErrorPackage)){
                StatsPackage statsPackage = (StatsPackage) aPackage;
                statisticsFields.add(new StatisticsField(statsPackage.getName(), statsPackage.getCe()));
            }
        });
        return statisticsFields;
    }

    public boolean writeXML(String path){
        try (PrintWriter out = new PrintWriter(new FileOutputStream("results.xml"))) {
            jdepend.xmlui.JDepend xml = new jdepend.xmlui.JDepend(out);
            xml.addDirectory(path);

            PackageFilter f = PackageFilter.all();
            f.accept("vn.edu");
            f.excluding("org");
            xml.setFilter(f);

            xml.analyze();
            return true;
        }
         catch (Exception e){
            return false;
         }

    }
}
