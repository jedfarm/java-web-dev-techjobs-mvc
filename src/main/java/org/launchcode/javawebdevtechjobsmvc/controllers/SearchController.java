package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;
/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @RequestMapping(value = "results", method=RequestMethod.POST)
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        List<Job> jobs;
        String toDisplay = searchType;
        if(searchType.toLowerCase() == "all" && searchTerm == ""){
            jobs = JobData.findAll();

        } else if (searchType.toLowerCase() == "all"){
            jobs = JobData.findByValue(searchTerm);

        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);

        }
        if (toDisplay.equalsIgnoreCase("CoreCompetency")){
            toDisplay = "Skills";
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("previousChoice", searchType);
        model.addAttribute("searchTitle", "Jobs with " + toDisplay + ": " + searchTerm);
        return "search";
    }

}
