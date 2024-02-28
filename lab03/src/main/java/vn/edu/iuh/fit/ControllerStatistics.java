package vn.edu.iuh.fit;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import vn.edu.iuh.fit.jdepend.dto.Package;
import vn.edu.iuh.fit.jdepend.dto.StatisticsField;
import vn.edu.iuh.fit.jdepend.parser.XMLParser;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/j")
public class ControllerStatistics {
    XMLParser xmlParser;
    @GetMapping("/gen-xml")
    public String genXML(
            Model model,
            @ModelAttribute(value = "path") Optional<String> path
    ){
        if(path.isEmpty())
            return "statistics";
        List<Package> packages = xmlParser.readXML("../lab03/" + path.get());

        List<StatisticsField> statisticsFieldsPie = xmlParser.statisticsPie(packages);
        model.addAttribute("path", path.get());
        model.addAttribute("dataPie",statisticsFieldsPie );
        model.addAttribute("nameChartPie", "Generate report (packages ok, packages not ok) : Ca,Ce <= 20");

        List<StatisticsField> statisticsFieldsBar = xmlParser.statisticsBar(packages);
        model.addAttribute("dataBar",statisticsFieldsBar );
        model.addAttribute("nameChartBar", "CA");

        List<StatisticsField> statisticsFieldsBarCe = xmlParser.statisticsBarCe(packages);
        model.addAttribute("dataBarCe",statisticsFieldsBarCe );
        model.addAttribute("nameChartBarCe", "CE");

        return "statistics";
    }
}
